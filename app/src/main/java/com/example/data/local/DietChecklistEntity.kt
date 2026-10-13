package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diet_checklist")
data class DietChecklistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val date: String, // Format: YYYY-MM-DD
    val title: String,
    val subtitle: String,
    val isCompleted: Boolean = false
)
