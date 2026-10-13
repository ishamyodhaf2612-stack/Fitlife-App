package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.DietChecklistEntity
import com.example.data.local.FitLifeDatabase
import com.example.data.local.FoodEntity
import com.example.data.local.InitialData
import com.example.data.local.MealLogEntity
import com.example.data.local.MealScheduleEntity
import com.example.data.local.UserProfileEntity
import com.example.data.local.WaterLogEntity
import com.example.data.local.WorkoutLogEntity
import com.example.data.model.Exercise
import com.example.data.model.WorkoutCatalog
import com.example.data.model.WorkoutProgram
import com.example.data.repository.FitLifeRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

data class ActiveWorkoutState(
    val isActive: Boolean = false,
    val program: WorkoutProgram? = null,
    val currentExerciseIndex: Int = 0,
    val isRestInterval: Boolean = false,
    val secondsRemaining: Int = 30,
    val totalSecondsElapsed: Int = 0,
    val isTimerRunning: Boolean = false,
    val totalCaloriesBurned: Int = 0,
    val completedExercisesCount: Int = 0
)

data class DayCalorieStat(
    val dateStr: String,
    val dayLabel: String,
    val consumedCalories: Int,
    val burnedCalories: Int
)

class FitLifeViewModel(application: Application) : AndroidViewModel(application) {

    private val database = FitLifeDatabase.getDatabase(application)
    private val repository = FitLifeRepository(database.fitLifeDao())

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val todayDate: String = dateFormat.format(Date())

    // Current selected date (allows viewing past days)
    private val _selectedDate = MutableStateFlow(todayDate)
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    // Active bottom navigation tab: 0=Kalori, 1=Air, 2=Diet, 3=Workout
    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    // Food search query
    private val _foodSearchQuery = MutableStateFlow("")
    val foodSearchQuery: StateFlow<String> = _foodSearchQuery.asStateFlow()

    // Foods list
    val foodSearchResults: StateFlow<List<FoodEntity>> = _foodSearchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) repository.allFoods
            else repository.searchFoods(query.trim())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // User Profile
    val userProfile: StateFlow<UserProfileEntity> = repository.userProfile
        .flatMapLatest { profile ->
            MutableStateFlow(profile ?: UserProfileEntity())
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfileEntity())

    // Meals for selected date
    val mealLogsForSelectedDate: StateFlow<List<MealLogEntity>> = _selectedDate
        .flatMapLatest { date -> repository.getMealLogsByDate(date) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Total calories consumed on selected date
    val totalCaloriesConsumed: StateFlow<Int> = _selectedDate
        .flatMapLatest { date -> repository.getTotalCaloriesByDate(date) }
        .flatMapLatest { total -> MutableStateFlow(total ?: 0) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Total calories burned on selected date
    val totalCaloriesBurned: StateFlow<Int> = _selectedDate
        .flatMapLatest { date -> repository.getTotalBurnedCaloriesByDate(date) }
        .flatMapLatest { total -> MutableStateFlow(total ?: 0) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    // Workouts for selected date
    val workoutLogsForSelectedDate: StateFlow<List<WorkoutLogEntity>> = _selectedDate
        .flatMapLatest { date -> repository.getWorkoutLogsByDate(date) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Water log for selected date
    val waterLogForSelectedDate: StateFlow<WaterLogEntity> = _selectedDate
        .flatMapLatest { date -> repository.getWaterLogByDate(date) }
        .flatMapLatest { log ->
            MutableStateFlow(
                log ?: WaterLogEntity(
                    date = _selectedDate.value,
                    amountMl = 0,
                    glasses = 0,
                    targetMl = (userProfile.value.dailyWaterTargetGlasses * 250)
                )
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            WaterLogEntity(date = todayDate, amountMl = 0, glasses = 0, targetMl = 2000)
        )

    // Recent 30 days water logs (for streak calculation)
    val recentWaterLogs: StateFlow<List<WaterLogEntity>> = repository.getRecentWaterLogs()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // All meals & workouts to generate 7-day stats
    private val allMealLogs = repository.getAllMealLogs()
    private val allWorkoutLogs = repository.getAllWorkoutLogs()

    val weeklyCalorieStats: StateFlow<List<DayCalorieStat>> = combine(allMealLogs, allWorkoutLogs) { meals, workouts ->
        val cal = Calendar.getInstance()
        val stats = mutableListOf<DayCalorieStat>()
        val dayNameFormat = SimpleDateFormat("EEE", Locale("id", "ID"))

        for (i in 6 downTo 0) {
            val dCal = Calendar.getInstance()
            dCal.add(Calendar.DAY_OF_YEAR, -i)
            val dStr = dateFormat.format(dCal.time)
            val dLabel = dayNameFormat.format(dCal.time)

            val consumed = meals.filter { it.date == dStr }.sumOf { it.calories }
            val burned = workouts.filter { it.date == dStr }.sumOf { it.caloriesBurned }

            stats.add(DayCalorieStat(dateStr = dStr, dayLabel = dLabel, consumedCalories = consumed, burnedCalories = burned))
        }
        stats
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Diet checklist for selected date
    val dietChecklist: StateFlow<List<DietChecklistEntity>> = _selectedDate
        .flatMapLatest { date -> repository.getDietChecklistByDate(date) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Meal schedules
    val mealSchedules: StateFlow<List<MealScheduleEntity>> = repository.mealSchedules
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active workout player state
    private val _workoutState = MutableStateFlow(ActiveWorkoutState())
    val workoutState: StateFlow<ActiveWorkoutState> = _workoutState.asStateFlow()

    private var workoutTimerJob: Job? = null

    // Backup & Export status feedback message
    private val _statusMessage = MutableStateFlow<String?>(null)
    val statusMessage: StateFlow<String?> = _statusMessage.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfNeeded(todayDate)
            initChecklistForDate(todayDate)
            initSchedulesIfEmpty()
        }
    }

    fun setTab(tab: Int) {
        _currentTab.value = tab
    }

    fun setSelectedDate(date: String) {
        _selectedDate.value = date
        viewModelScope.launch {
            initChecklistForDate(date)
        }
    }

    fun setFoodSearchQuery(query: String) {
        _foodSearchQuery.value = query
    }

    fun clearStatusMessage() {
        _statusMessage.value = null
    }

    // --- CALORIE ACTIONS ---
    fun addMealLog(food: FoodEntity, mealType: String, servingCount: Float = 1.0f) {
        viewModelScope.launch {
            val calories = (food.calories * servingCount).toInt()
            val protein = food.protein * servingCount
            val carbs = food.carbs * servingCount
            val fat = food.fat * servingCount

            repository.insertMealLog(
                MealLogEntity(
                    date = _selectedDate.value,
                    mealType = mealType,
                    foodName = food.name,
                    calories = calories,
                    protein = protein,
                    carbs = carbs,
                    fat = fat,
                    servingCount = servingCount
                )
            )
            _statusMessage.value = "Berhasil mencatat ${food.name} ke $mealType"
        }
    }

    fun addCustomFood(
        name: String,
        category: String,
        calories: Int,
        protein: Float,
        carbs: Float,
        fat: Float,
        servingUnit: String,
        autoAddToMealType: String? = null
    ) {
        viewModelScope.launch {
            val foodEntity = FoodEntity(
                name = name,
                category = category,
                calories = calories,
                protein = protein,
                carbs = carbs,
                fat = fat,
                servingUnit = servingUnit,
                isCustom = true
            )
            repository.insertCustomFood(foodEntity)

            if (autoAddToMealType != null) {
                addMealLog(foodEntity, autoAddToMealType, 1.0f)
            } else {
                _statusMessage.value = "Makanan custom '$name' berhasil disimpan!"
            }
        }
    }

    fun deleteMealLog(id: Long) {
        viewModelScope.launch {
            repository.deleteMealLog(id)
            _statusMessage.value = "Item makanan dihapus"
        }
    }

    // --- WATER ACTIONS ---
    fun addWater(amountMl: Int = 250) {
        viewModelScope.launch {
            val current = waterLogForSelectedDate.value
            val newAmount = (current.amountMl + amountMl).coerceAtLeast(0)
            val newGlasses = (newAmount / 250)
            val target = if (current.targetMl > 0) current.targetMl else userProfile.value.dailyWaterTargetGlasses * 250

            repository.logWater(
                date = _selectedDate.value,
                amountMl = newAmount,
                glasses = newGlasses,
                targetMl = target
            )
        }
    }

    fun setWaterTargetGlasses(glasses: Int) {
        viewModelScope.launch {
            val p = userProfile.value.copy(dailyWaterTargetGlasses = glasses)
            repository.saveUserProfile(p)
            val current = waterLogForSelectedDate.value
            repository.logWater(
                date = _selectedDate.value,
                amountMl = current.amountMl,
                glasses = current.glasses,
                targetMl = glasses * 250
            )
            _statusMessage.value = "Target air diperbarui: $glasses gelas (${glasses * 250} ml)"
        }
    }

    fun calculateWaterStreak(): Int {
        val logs = recentWaterLogs.value
        if (logs.isEmpty()) return 0

        var streak = 0
        val cal = Calendar.getInstance()
        var checkDate = dateFormat.format(cal.time)

        // Check if today reached target or check from yesterday
        val todayLog = logs.find { it.date == checkDate }
        if (todayLog != null && todayLog.amountMl >= todayLog.targetMl && todayLog.targetMl > 0) {
            streak++
        }

        cal.add(Calendar.DAY_OF_YEAR, -1)
        while (true) {
            checkDate = dateFormat.format(cal.time)
            val log = logs.find { it.date == checkDate }
            if (log != null && log.amountMl >= log.targetMl && log.targetMl > 0) {
                streak++
                cal.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }
        return streak
    }

    // --- DIET ACTIONS ---
    private suspend fun initChecklistForDate(date: String) {
        val existing = repository.getDietChecklistByDate(date)
        // Check if items are already present, if not add defaults
        combine(existing) { items -> items }.collect { list ->
            if (list.first().isEmpty()) {
                val defaultEntities = InitialData.defaultChecklistTitles.map { (title, subtitle) ->
                    DietChecklistEntity(
                        date = date,
                        title = title,
                        subtitle = subtitle,
                        isCompleted = false
                    )
                }
                repository.addCustomChecklistItem(defaultEntities.first())
                for (item in defaultEntities.drop(1)) {
                    repository.addCustomChecklistItem(item)
                }
            }
        }
    }

    private suspend fun initSchedulesIfEmpty() {
        // ViewModel initial seeding for default meal schedules
        val defaultList = InitialData.defaultMealSchedules
        for (item in defaultList) {
            repository.addMealSchedule(item)
        }
    }

    fun toggleChecklistItem(item: DietChecklistEntity) {
        viewModelScope.launch {
            repository.updateChecklistItem(item.copy(isCompleted = !item.isCompleted))
        }
    }

    fun addCustomChecklistItem(title: String, subtitle: String) {
        viewModelScope.launch {
            repository.addCustomChecklistItem(
                DietChecklistEntity(
                    date = _selectedDate.value,
                    title = title,
                    subtitle = subtitle,
                    isCompleted = false
                )
            )
            _statusMessage.value = "Target checklist baru ditambahkan!"
        }
    }

    fun updateMealSchedule(schedule: MealScheduleEntity) {
        viewModelScope.launch {
            repository.updateMealSchedule(schedule)
            _statusMessage.value = "Jadwal ${schedule.mealType} disimpan"
        }
    }

    // --- WORKOUT ACTIONS ---
    fun startWorkoutProgram(program: WorkoutProgram) {
        workoutTimerJob?.cancel()
        val firstExercise = program.exercises.firstOrNull() ?: return

        _workoutState.value = ActiveWorkoutState(
            isActive = true,
            program = program,
            currentExerciseIndex = 0,
            isRestInterval = false,
            secondsRemaining = firstExercise.defaultDurationSec,
            totalSecondsElapsed = 0,
            isTimerRunning = true,
            totalCaloriesBurned = 0,
            completedExercisesCount = 0
        )

        startWorkoutTimerLoop()
    }

    private fun startWorkoutTimerLoop() {
        workoutTimerJob?.cancel()
        workoutTimerJob = viewModelScope.launch {
            while (_workoutState.value.isActive && _workoutState.value.isTimerRunning) {
                delay(1000)
                val current = _workoutState.value
                val newElapsed = current.totalSecondsElapsed + 1
                val newSecRem = current.secondsRemaining - 1

                val activeProg = current.program
                val activeEx = activeProg?.exercises?.getOrNull(current.currentExerciseIndex)

                // Accumulate calories burned continuously
                val calPerSec = (activeEx?.caloriesBurnPerMinute ?: 8) / 60.0f
                val updatedCal = (newElapsed * calPerSec).toInt()

                if (newSecRem <= 0) {
                    if (current.isRestInterval) {
                        // Rest finished -> move to next exercise
                        val nextIndex = current.currentExerciseIndex + 1
                        if (activeProg != null && nextIndex < activeProg.exercises.size) {
                            val nextEx = activeProg.exercises[nextIndex]
                            _workoutState.value = current.copy(
                                currentExerciseIndex = nextIndex,
                                isRestInterval = false,
                                secondsRemaining = nextEx.defaultDurationSec,
                                totalSecondsElapsed = newElapsed,
                                totalCaloriesBurned = updatedCal
                            )
                        } else {
                            // Finished entire program!
                            finishActiveWorkout()
                            break
                        }
                    } else {
                        // Active exercise finished -> start 20s rest interval
                        val completedCount = current.completedExercisesCount + 1
                        val nextIndex = current.currentExerciseIndex + 1
                        if (activeProg != null && nextIndex < activeProg.exercises.size) {
                            _workoutState.value = current.copy(
                                isRestInterval = true,
                                secondsRemaining = 20, // 20s rest interval
                                totalSecondsElapsed = newElapsed,
                                totalCaloriesBurned = updatedCal,
                                completedExercisesCount = completedCount
                            )
                        } else {
                            // Last exercise finished!
                            finishActiveWorkout()
                            break
                        }
                    }
                } else {
                    _workoutState.value = current.copy(
                        secondsRemaining = newSecRem,
                        totalSecondsElapsed = newElapsed,
                        totalCaloriesBurned = updatedCal
                    )
                }
            }
        }
    }

    fun pauseResumeWorkoutTimer() {
        val isRunning = _workoutState.value.isTimerRunning
        _workoutState.value = _workoutState.value.copy(isTimerRunning = !isRunning)
        if (!isRunning) {
            startWorkoutTimerLoop()
        } else {
            workoutTimerJob?.cancel()
        }
    }

    fun skipToNextExercise() {
        val current = _workoutState.value
        val activeProg = current.program ?: return
        val nextIndex = current.currentExerciseIndex + 1

        if (nextIndex < activeProg.exercises.size) {
            val nextEx = activeProg.exercises[nextIndex]
            _workoutState.value = current.copy(
                currentExerciseIndex = nextIndex,
                isRestInterval = false,
                secondsRemaining = nextEx.defaultDurationSec,
                completedExercisesCount = current.completedExercisesCount + 1
            )
        } else {
            finishActiveWorkout()
        }
    }

    fun cancelActiveWorkout() {
        workoutTimerJob?.cancel()
        _workoutState.value = ActiveWorkoutState(isActive = false)
    }

    fun finishActiveWorkout() {
        workoutTimerJob?.cancel()
        val current = _workoutState.value
        val program = current.program

        if (program != null && current.totalSecondsElapsed > 10) {
            val calories = if (current.totalCaloriesBurned > 0) current.totalCaloriesBurned
            else program.estimatedCalories

            viewModelScope.launch {
                repository.insertWorkoutLog(
                    WorkoutLogEntity(
                        date = _selectedDate.value,
                        workoutTitle = program.title,
                        durationSeconds = current.totalSecondsElapsed,
                        caloriesBurned = calories,
                        exercisesCompleted = current.completedExercisesCount.coerceAtLeast(1)
                    )
                )
                _statusMessage.value = "Hebat! Latihan '${program.title}' selesai. -$calories kkal!"
            }
        }

        _workoutState.value = ActiveWorkoutState(isActive = false)
    }

    // --- PROFILE & BMR/TDEE CALCULATION ---
    fun updateProfile(
        name: String,
        weightKg: Float,
        heightCm: Float,
        age: Int,
        gender: String,
        activityLevel: String,
        goal: String,
        manualCalorieTarget: Int? = null,
        waterGlasses: Int? = null
    ) {
        viewModelScope.launch {
            // Auto BMR Calculation via Mifflin-St Jeor Formula
            val bmr = if (gender == "Pria") {
                (10 * weightKg) + (6.25f * heightCm) - (5 * age) + 5
            } else {
                (10 * weightKg) + (6.25f * heightCm) - (5 * age) - 161
            }

            val activityMultiplier = when (activityLevel) {
                "Sedenter (Jarang Olahraga)" -> 1.2f
                "Ringan (1-3 hari/minggu)" -> 1.375f
                "Sedang (3-5 hari/minggu)" -> 1.55f
                "Sangat Aktif (6-7 hari/minggu)" -> 1.725f
                else -> 1.4f
            }

            val tdee = (bmr * activityMultiplier).toInt()

            val calculatedTarget = manualCalorieTarget ?: when {
                goal.contains("Defisit") -> (tdee - 450).coerceAtLeast(1200)
                goal.contains("Surplus") -> (tdee + 350)
                else -> tdee
            }

            val targetWater = waterGlasses ?: userProfile.value.dailyWaterTargetGlasses

            val updated = userProfile.value.copy(
                name = name,
                weightKg = weightKg,
                heightCm = heightCm,
                age = age,
                gender = gender,
                activityLevel = activityLevel,
                goal = goal,
                dailyCalorieTarget = calculatedTarget,
                dailyWaterTargetGlasses = targetWater
            )

            repository.saveUserProfile(updated)
            _statusMessage.value = "Profil & Target Kalori ($calculatedTarget kkal) berhasil diperbarui!"
        }
    }

    // --- BACKUP EXPORT & IMPORT ---
    fun exportBackup(onResult: (String) -> Unit) {
        viewModelScope.launch {
            val json = repository.exportDataToJson()
            onResult(json)
            _statusMessage.value = "Backup data JSON berhasil dibuat!"
        }
    }

    fun importBackup(jsonString: String) {
        viewModelScope.launch {
            val success = repository.importDataFromJson(jsonString)
            if (success) {
                _statusMessage.value = "Data berhasil dipulihkan dari backup!"
            } else {
                _statusMessage.value = "Gagal memproses file JSON backup. Pastikan format valid."
            }
        }
    }
}
