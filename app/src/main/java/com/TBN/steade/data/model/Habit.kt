package com.TBN.steade.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val category: String,
    val frequency: String,
    val target: Int,
    val icon: String,
    val isActive: Boolean = true,
    val isCompletedToday: Boolean = false,
    val type: HabitType = HabitType.BASIC
)

enum class HabitType {
    BASIC, CUSTOM
}
