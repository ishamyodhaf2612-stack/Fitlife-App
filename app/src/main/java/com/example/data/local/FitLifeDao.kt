package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface FitLifeDao {

    // --- FOOD ITEMS ---
    @Query("SELECT * FROM food_items ORDER BY name ASC")
    fun getAllFoodItems(): Flow<List<FoodEntity>>

    @Query("SELECT * FROM food_items WHERE name LIKE '%' || :query || '%' OR category LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchFoodItems(query: String): Flow<List<FoodEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: FoodEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllFoods(foods: List<FoodEntity>)

    @Query("SELECT COUNT(*) FROM food_items")
    suspend fun getFoodCount(): Int

    // --- MEAL LOGS ---
    @Query("SELECT * FROM meal_logs WHERE date = :date ORDER BY timestamp DESC")
    fun getMealLogsByDate(date: String): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM meal_logs ORDER BY date DESC, timestamp DESC")
    fun getAllMealLogs(): Flow<List<MealLogEntity>>

    @Query("SELECT * FROM meal_logs ORDER BY date DESC, timestamp DESC")
    suspend fun getAllMealLogsOnce(): List<MealLogEntity>

    @Query("SELECT SUM(calories) FROM meal_logs WHERE date = :date")
    fun getTotalCaloriesByDate(date: String): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealLog(mealLog: MealLogEntity): Long

    @Query("DELETE FROM meal_logs WHERE id = :id")
    suspend fun deleteMealLog(id: Long)

    @Query("DELETE FROM meal_logs WHERE date = :date")
    suspend fun clearMealLogsForDate(date: String)

    // --- WATER LOGS ---
    @Query("SELECT * FROM water_logs WHERE date = :date")
    fun getWaterLogByDate(date: String): Flow<WaterLogEntity?>

    @Query("SELECT * FROM water_logs ORDER BY date DESC LIMIT 30")
    fun getRecentWaterLogs(): Flow<List<WaterLogEntity>>

    @Query("SELECT * FROM water_logs ORDER BY date DESC")
    suspend fun getAllWaterLogsOnce(): List<WaterLogEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateWaterLog(waterLog: WaterLogEntity)

    // --- WORKOUT LOGS ---
    @Query("SELECT * FROM workout_logs WHERE date = :date ORDER BY timestamp DESC")
    fun getWorkoutLogsByDate(date: String): Flow<List<WorkoutLogEntity>>

    @Query("SELECT * FROM workout_logs ORDER BY timestamp DESC")
    fun getAllWorkoutLogs(): Flow<List<WorkoutLogEntity>>

    @Query("SELECT * FROM workout_logs ORDER BY timestamp DESC")
    suspend fun getAllWorkoutLogsOnce(): List<WorkoutLogEntity>

    @Query("SELECT SUM(caloriesBurned) FROM workout_logs WHERE date = :date")
    fun getTotalBurnedCaloriesByDate(date: String): Flow<Int?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkoutLog(log: WorkoutLogEntity): Long

    @Query("DELETE FROM workout_logs WHERE id = :id")
    suspend fun deleteWorkoutLog(id: Long)

    // --- DIET CHECKLIST ---
    @Query("SELECT * FROM diet_checklist WHERE date = :date ORDER BY id ASC")
    fun getDietChecklistByDate(date: String): Flow<List<DietChecklistEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChecklistItem(item: DietChecklistEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllChecklist(items: List<DietChecklistEntity>)

    @Update
    suspend fun updateChecklistItem(item: DietChecklistEntity)

    // --- MEAL SCHEDULE ---
    @Query("SELECT * FROM meal_schedule ORDER BY id ASC")
    fun getAllMealSchedules(): Flow<List<MealScheduleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMealSchedule(schedule: MealScheduleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMealSchedules(schedules: List<MealScheduleEntity>)

    @Update
    suspend fun updateMealSchedule(schedule: MealScheduleEntity)

    @Query("DELETE FROM meal_schedule WHERE id = :id")
    suspend fun deleteMealSchedule(id: Long)

    // --- USER PROFILE ---
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Query("SELECT * FROM user_profile WHERE id = 1")
    suspend fun getUserProfileOnce(): UserProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUserProfile(profile: UserProfileEntity)
}
