package com.TBN.steade.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.TBN.steade.data.model.Habit
import com.TBN.steade.data.model.User

@Database(entities = [User::class, Habit::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun habitDao(): HabitDao
}
