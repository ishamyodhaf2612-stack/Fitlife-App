package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_logs")
data class WorkoutLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format: YYYY-MM-DD
    val workoutTitle: String,
    val durationSeconds: Int,
    val caloriesBurned: Int,
    val exercisesCompleted: Int,
    val timestamp: Long = System.currentTimeMillis()
)
