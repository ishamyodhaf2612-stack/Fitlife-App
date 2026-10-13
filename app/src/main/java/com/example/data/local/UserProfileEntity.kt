package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey
    val id: Int = 1,
    val name: String = "Sobat FitLife",
    val weightKg: Float = 65f,
    val heightCm: Float = 170f,
    val age: Int = 25,
    val gender: String = "Pria", // "Pria" atau "Wanita"
    val activityLevel: String = "Sedang", // "Sedenter", "Ringan", "Sedang", "Sangat Aktif"
    val goal: String = "Defisit Kalori (Turun Berat)", // "Defisit", "Maintain", "Surplus"
    val dailyCalorieTarget: Int = 2000,
    val dailyWaterTargetGlasses: Int = 8,
    val waterReminderIntervalHours: Int = 2,
    val isWaterReminderEnabled: Boolean = true
)
