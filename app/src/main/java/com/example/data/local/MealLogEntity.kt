package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_logs")
data class MealLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format: YYYY-MM-DD
    val mealType: String, // "SARAPAN", "MAKAN_SIANG", "MAKAN_MALAM", "CAMILAN"
    val foodName: String,
    val calories: Int,
    val protein: Float,
    val carbs: Float,
    val fat: Float,
    val servingCount: Float = 1.0f,
    val timestamp: Long = System.currentTimeMillis()
)
