package com.TBN.steade.data.local

import androidx.room.*
import com.TBN.steade.data.model.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Query("SELECT * FROM habits")
    fun getAllHabits(): Flow<List<Habit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: Habit)

    @Update
    suspend fun updateHabit(habit: Habit)

    @Delete
    suspend fun deleteHabit(habit: Habit)

    @Query("UPDATE habits SET isCompletedToday = :completed WHERE id = :habitId")
    suspend fun updateHabitCompletion(habitId: Int, completed: Boolean)
}
