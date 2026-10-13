package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal_schedule")
data class MealScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val mealType: String, // "Sarapan", "Makan Siang", "Camilan Sore", "Makan Malam"
    val scheduledTime: String, // e.g. "07:30", "12:30", "16:00", "19:00"
    val customMenuNotes: String, // e.g. "Oatmeal pisang + telur rebus"
    val isEnabled: Boolean = true
)
