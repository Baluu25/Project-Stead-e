package com.TBN.steade.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: String = "current_user",
    val name: String,
    val age: Int,
    val region: String,
    val weight: Double,
    val height: Double,
    val goals: String,
    val badHabits: String,
    val goodHabits: String,
    val gender: String,
    val cycleData: String? = null
)
