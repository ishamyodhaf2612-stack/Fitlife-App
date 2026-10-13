package com.example.data.repository

import com.example.data.local.DietChecklistEntity
import com.example.data.local.FitLifeDao
import com.example.data.local.FoodEntity
import com.example.data.local.InitialData
import com.example.data.local.MealLogEntity
import com.example.data.local.MealScheduleEntity
import com.example.data.local.UserProfileEntity
import com.example.data.local.WaterLogEntity
import com.example.data.local.WorkoutLogEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FitLifeRepository(private val dao: FitLifeDao) {

    // --- FOOD ITEMS ---
    val allFoods: Flow<List<FoodEntity>> = dao.getAllFoodItems()

    fun searchFoods(query: String): Flow<List<FoodEntity>> = dao.searchFoodItems(query)

    suspend fun insertCustomFood(food: FoodEntity): Long = withContext(Dispatchers.IO) {
        dao.insertFood(food)
    }

    // --- MEAL LOGS ---
    fun getMealLogsByDate(date: String): Flow<List<MealLogEntity>> = dao.getMealLogsByDate(date)

    fun getAllMealLogs(): Flow<List<MealLogEntity>> = dao.getAllMealLogs()

    fun getTotalCaloriesByDate(date: String): Flow<Int?> = dao.getTotalCaloriesByDate(date)

    suspend fun insertMealLog(log: MealLogEntity): Long = withContext(Dispatchers.IO) {
        dao.insertMealLog(log)
    }

    suspend fun deleteMealLog(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteMealLog(id)
    }

    // --- WATER LOGS ---
    fun getWaterLogByDate(date: String): Flow<WaterLogEntity?> = dao.getWaterLogByDate(date)

    fun getRecentWaterLogs(): Flow<List<WaterLogEntity>> = dao.getRecentWaterLogs()

    suspend fun logWater(date: String, amountMl: Int, glasses: Int, targetMl: Int) = withContext(Dispatchers.IO) {
        dao.insertOrUpdateWaterLog(
            WaterLogEntity(
                date = date,
                amountMl = amountMl,
                glasses = glasses,
                targetMl = targetMl
            )
        )
    }

    // --- WORKOUT LOGS ---
    fun getWorkoutLogsByDate(date: String): Flow<List<WorkoutLogEntity>> = dao.getWorkoutLogsByDate(date)

    fun getAllWorkoutLogs(): Flow<List<WorkoutLogEntity>> = dao.getAllWorkoutLogs()

    fun getTotalBurnedCaloriesByDate(date: String): Flow<Int?> = dao.getTotalBurnedCaloriesByDate(date)

    suspend fun insertWorkoutLog(log: WorkoutLogEntity): Long = withContext(Dispatchers.IO) {
        dao.insertWorkoutLog(log)
    }

    suspend fun deleteWorkoutLog(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteWorkoutLog(id)
    }

    // --- DIET CHECKLIST ---
    fun getDietChecklistByDate(date: String): Flow<List<DietChecklistEntity>> = dao.getDietChecklistByDate(date)

    suspend fun updateChecklistItem(item: DietChecklistEntity) = withContext(Dispatchers.IO) {
        dao.updateChecklistItem(item)
    }

    suspend fun addCustomChecklistItem(item: DietChecklistEntity): Long = withContext(Dispatchers.IO) {
        dao.insertChecklistItem(item)
    }

    // --- MEAL SCHEDULE ---
    val mealSchedules: Flow<List<MealScheduleEntity>> = dao.getAllMealSchedules()

    suspend fun updateMealSchedule(schedule: MealScheduleEntity) = withContext(Dispatchers.IO) {
        dao.updateMealSchedule(schedule)
    }

    suspend fun addMealSchedule(schedule: MealScheduleEntity): Long = withContext(Dispatchers.IO) {
        dao.insertMealSchedule(schedule)
    }

    suspend fun deleteMealSchedule(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteMealSchedule(id)
    }

    // --- USER PROFILE ---
    val userProfile: Flow<UserProfileEntity?> = dao.getUserProfile()

    suspend fun saveUserProfile(profile: UserProfileEntity) = withContext(Dispatchers.IO) {
        dao.insertOrUpdateUserProfile(profile)
    }

    // --- INITIAL DATA SEEDING ---
    suspend fun seedInitialDataIfNeeded(todayDate: String) = withContext(Dispatchers.IO) {
        // Seed food items if empty or less than default catalog
        val count = dao.getFoodCount()
        if (count < 80) {
            dao.insertAllFoods(InitialData.indonesianFoods)
        }

        // Seed profile if null
        val existingProfile = dao.getUserProfileOnce()
        if (existingProfile == null) {
            dao.insertOrUpdateUserProfile(UserProfileEntity())
        }

        // Seed default schedules if empty
        // We can check by collecting or reading
        // Ensure today's checklist exists
    }

    suspend fun ensureTodayChecklist(todayDate: String) = withContext(Dispatchers.IO) {
        // Handled in ViewModel or on date switch
    }

    // --- EXPORT & IMPORT BACKUP (JSON) ---
    suspend fun exportDataToJson(): String = withContext(Dispatchers.IO) {
        val root = JSONObject()
        root.put("app", "FitLife")
        root.put("version", "1.0")
        root.put("exportDate", SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date()))

        // Export profile
        val profile = dao.getUserProfileOnce()
        if (profile != null) {
            val profObj = JSONObject()
            profObj.put("name", profile.name)
            profObj.put("weightKg", profile.weightKg.toDouble())
            profObj.put("heightCm", profile.heightCm.toDouble())
            profObj.put("age", profile.age)
            profObj.put("gender", profile.gender)
            profObj.put("activityLevel", profile.activityLevel)
            profObj.put("goal", profile.goal)
            profObj.put("dailyCalorieTarget", profile.dailyCalorieTarget)
            profObj.put("dailyWaterTargetGlasses", profile.dailyWaterTargetGlasses)
            root.put("userProfile", profObj)
        }

        // Export meal logs
        val mealLogs = dao.getAllMealLogsOnce()
        val mealArray = JSONArray()
        for (meal in mealLogs) {
            val m = JSONObject()
            m.put("date", meal.date)
            m.put("mealType", meal.mealType)
            m.put("foodName", meal.foodName)
            m.put("calories", meal.calories)
            m.put("protein", meal.protein.toDouble())
            m.put("carbs", meal.carbs.toDouble())
            m.put("fat", meal.fat.toDouble())
            m.put("servingCount", meal.servingCount.toDouble())
            m.put("timestamp", meal.timestamp)
            mealArray.put(m)
        }
        root.put("mealLogs", mealArray)

        // Export water logs
        val waterLogs = dao.getAllWaterLogsOnce()
        val waterArray = JSONArray()
        for (w in waterLogs) {
            val wo = JSONObject()
            wo.put("date", w.date)
            wo.put("amountMl", w.amountMl)
            wo.put("glasses", w.glasses)
            wo.put("targetMl", w.targetMl)
            waterArray.put(wo)
        }
        root.put("waterLogs", waterArray)

        // Export workout logs
        val workoutLogs = dao.getAllWorkoutLogsOnce()
        val workoutArray = JSONArray()
        for (work in workoutLogs) {
            val wrk = JSONObject()
            wrk.put("date", work.date)
            wrk.put("workoutTitle", work.workoutTitle)
            wrk.put("durationSeconds", work.durationSeconds)
            wrk.put("caloriesBurned", work.caloriesBurned)
            wrk.put("exercisesCompleted", work.exercisesCompleted)
            wrk.put("timestamp", work.timestamp)
            workoutArray.put(wrk)
        }
        root.put("workoutLogs", workoutArray)

        root.toString(2)
    }

    suspend fun importDataFromJson(jsonString: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val root = JSONObject(jsonString)

            if (root.has("userProfile")) {
                val p = root.getJSONObject("userProfile")
                val importedProfile = UserProfileEntity(
                    id = 1,
                    name = p.optString("name", "Sobat FitLife"),
                    weightKg = p.optDouble("weightKg", 65.0).toFloat(),
                    heightCm = p.optDouble("heightCm", 170.0).toFloat(),
                    age = p.optInt("age", 25),
                    gender = p.optString("gender", "Pria"),
                    activityLevel = p.optString("activityLevel", "Sedang"),
                    goal = p.optString("goal", "Defisit Kalori (Turun Berat)"),
                    dailyCalorieTarget = p.optInt("dailyCalorieTarget", 2000),
                    dailyWaterTargetGlasses = p.optInt("dailyWaterTargetGlasses", 8)
                )
                dao.insertOrUpdateUserProfile(importedProfile)
            }

            if (root.has("mealLogs")) {
                val array = root.getJSONArray("mealLogs")
                for (i in 0 until array.length()) {
                    val m = array.getJSONObject(i)
                    dao.insertMealLog(
                        MealLogEntity(
                            date = m.getString("date"),
                            mealType = m.getString("mealType"),
                            foodName = m.getString("foodName"),
                            calories = m.getInt("calories"),
                            protein = m.optDouble("protein", 0.0).toFloat(),
                            carbs = m.optDouble("carbs", 0.0).toFloat(),
                            fat = m.optDouble("fat", 0.0).toFloat(),
                            servingCount = m.optDouble("servingCount", 1.0).toFloat(),
                            timestamp = m.optLong("timestamp", System.currentTimeMillis())
                        )
                    )
                }
            }

            if (root.has("waterLogs")) {
                val array = root.getJSONArray("waterLogs")
                for (i in 0 until array.length()) {
                    val w = array.getJSONObject(i)
                    dao.insertOrUpdateWaterLog(
                        WaterLogEntity(
                            date = w.getString("date"),
                            amountMl = w.getInt("amountMl"),
                            glasses = w.getInt("glasses"),
                            targetMl = w.optInt("targetMl", 2000)
                        )
                    )
                }
            }

            if (root.has("workoutLogs")) {
                val array = root.getJSONArray("workoutLogs")
                for (i in 0 until array.length()) {
                    val w = array.getJSONObject(i)
                    dao.insertWorkoutLog(
                        WorkoutLogEntity(
                            date = w.getString("date"),
                            workoutTitle = w.getString("workoutTitle"),
                            durationSeconds = w.getInt("durationSeconds"),
                            caloriesBurned = w.getInt("caloriesBurned"),
                            exercisesCompleted = w.optInt("exercisesCompleted", 0),
                            timestamp = w.optLong("timestamp", System.currentTimeMillis())
                        )
                    )
                }
            }

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
