package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "water_logs")
data class WaterLogEntity(
    @PrimaryKey
    val date: String, // Format: YYYY-MM-DD
    val amountMl: Int,
    val glasses: Int,
    val targetMl: Int = 2000
)
