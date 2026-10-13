package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.FitLifeBottomBar
import com.example.ui.components.FitLifeTopBar
import com.example.ui.components.WorkoutTimerDialog
import com.example.ui.screens.calorie.CalorieScreen
import com.example.ui.screens.diet.DietScreen
import com.example.ui.screens.profile.ProfileSettingsDialog
import com.example.ui.screens.water.WaterScreen
import com.example.ui.screens.workout.WorkoutScreen
import com.example.ui.theme.FitLifeTheme
import com.example.viewmodel.FitLifeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitLifeTheme {
                FitLifeApp()
            }
        }
    }
}

@Composable
fun FitLifeApp(viewModel: FitLifeViewModel = viewModel()) {
    val selectedDate by viewModel.selectedDate.collectAsStateWithLifecycle()
    val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()

    val mealLogs by viewModel.mealLogsForSelectedDate.collectAsStateWithLifecycle()
    val totalCaloriesConsumed by viewModel.totalCaloriesConsumed.collectAsStateWithLifecycle()
    val totalCaloriesBurned by viewModel.totalCaloriesBurned.collectAsStateWithLifecycle()
    val weeklyStats by viewModel.weeklyCalorieStats.collectAsStateWithLifecycle()
    val foodSearchResults by viewModel.foodSearchResults.collectAsStateWithLifecycle()
    val foodSearchQuery by viewModel.foodSearchQuery.collectAsStateWithLifecycle()

    val waterLog by viewModel.waterLogForSelectedDate.collectAsStateWithLifecycle()
    val recentWaterLogs by viewModel.recentWaterLogs.collectAsStateWithLifecycle()

    val dietChecklist by viewModel.dietChecklist.collectAsStateWithLifecycle()
    val mealSchedules by viewModel.mealSchedules.collectAsStateWithLifecycle()

    val workoutLogs by viewModel.workoutLogsForSelectedDate.collectAsStateWithLifecycle()
    val workoutState by viewModel.workoutState.collectAsStateWithLifecycle()

    val statusMessage by viewModel.statusMessage.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    var showProfileDialog by remember { mutableStateOf(false) }

    LaunchedEffect(statusMessage) {
        statusMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearStatusMessage()
        }
    }

    Scaffold(
        topBar = {
            FitLifeTopBar(
                selectedDate = selectedDate,
                todayDate = viewModel.todayDate,
                onDateChanged = { viewModel.setSelectedDate(it) },
                onOpenProfileSettings = { showProfileDialog = true }
            )
        },
        bottomBar = {
            FitLifeBottomBar(
                currentTab = currentTab,
                onTabSelected = { viewModel.setTab(it) }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            AnimatedContent(
                targetState = currentTab,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "tabContentAnimation"
            ) { targetTab ->
                when (targetTab) {
                    0 -> CalorieScreen(
                        userProfile = userProfile,
                        mealLogs = mealLogs,
                        totalConsumed = totalCaloriesConsumed,
                        totalBurned = totalCaloriesBurned,
                        weeklyStats = weeklyStats,
                        foodSearchResults = foodSearchResults,
                        searchQuery = foodSearchQuery,
                        onSearchQueryChange = { viewModel.setFoodSearchQuery(it) },
                        onAddMeal = { food, mealType, serving ->
                            viewModel.addMealLog(food, mealType, serving)
                        },
                        onAddCustomFood = { name, cat, cal, prot, carb, fat, unit, autoMealType ->
                            viewModel.addCustomFood(name, cat, cal, prot, carb, fat, unit, autoMealType)
                        },
                        onDeleteMealLog = { viewModel.deleteMealLog(it) },
                        onOpenBmrCalculator = { showProfileDialog = true }
                    )

                    1 -> WaterScreen(
                        waterLog = waterLog,
                        userProfile = userProfile,
                        recentLogs = recentWaterLogs,
                        streakDays = viewModel.calculateWaterStreak(),
                        onAddWater = { viewModel.addWater(it) },
                        onSetTargetGlasses = { viewModel.setWaterTargetGlasses(it) },
                        onUpdateReminder = { enabled, interval ->
                            viewModel.updateProfile(
                                name = userProfile.name,
                                weightKg = userProfile.weightKg,
                                heightCm = userProfile.heightCm,
                                age = userProfile.age,
                                gender = userProfile.gender,
                                activityLevel = userProfile.activityLevel,
                                goal = userProfile.goal,
                                manualCalorieTarget = userProfile.dailyCalorieTarget,
                                waterGlasses = userProfile.dailyWaterTargetGlasses
                            )
                        }
                    )

                    2 -> DietScreen(
                        checklist = dietChecklist,
                        mealSchedules = mealSchedules,
                        onToggleChecklist = { viewModel.toggleChecklistItem(it) },
                        onAddChecklist = { title, desc -> viewModel.addCustomChecklistItem(title, desc) },
                        onUpdateSchedule = { viewModel.updateMealSchedule(it) }
                    )

                    3 -> WorkoutScreen(
                        workoutLogs = workoutLogs,
                        totalBurnedToday = totalCaloriesBurned,
                        onStartProgram = { viewModel.startWorkoutProgram(it) }
                    )
                }
            }
        }
    }

    // Active Workout Session Overlay Dialog
    if (workoutState.isActive) {
        WorkoutTimerDialog(
            workoutState = workoutState,
            onPauseResume = { viewModel.pauseResumeWorkoutTimer() },
            onSkipNext = { viewModel.skipToNextExercise() },
            onFinish = { viewModel.finishActiveWorkout() },
            onCancel = { viewModel.cancelActiveWorkout() }
        )
    }

    // Profile & BMR Calculator & Backup Dialog
    if (showProfileDialog) {
        ProfileSettingsDialog(
            profile = userProfile,
            onSaveProfile = { name, w, h, a, gender, act, goal, cal, water ->
                viewModel.updateProfile(name, w, h, a, gender, act, goal, cal, water)
            },
            onExportBackup = { callback ->
                viewModel.exportBackup(callback)
            },
            onImportBackup = { json ->
                viewModel.importBackup(json)
            },
            onDismiss = { showProfileDialog = false }
        )
    }
}
