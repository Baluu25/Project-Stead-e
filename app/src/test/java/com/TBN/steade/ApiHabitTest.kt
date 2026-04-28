package com.TBN.steade

import com.TBN.steade.data.network.ApiHabit
import org.junit.Assert.*
import org.junit.Test

class ApiHabitTest {

    @Test
    fun `isActive returns true when isActiveRaw is true`() {
        val habit = ApiHabit(isActiveRaw = true)
        assertTrue(habit.isActive)
    }

    @Test
    fun `isActive returns false when isActiveRaw is zero`() {
        val habit = ApiHabit(isActiveRaw = 0)
        assertFalse(habit.isActive)
    }

    @Test
    fun `isActive returns true when isActiveRaw is string 1`() {
        val habit = ApiHabit(isActiveRaw = "1")
        assertTrue(habit.isActive)
    }

    @Test
    fun `isCompletedToday returns true when completedToday is greater than zero`() {
        val habit = ApiHabit(completedToday = 3)
        assertTrue(habit.isCompletedToday)
    }

    @Test
    fun `isCompletedToday returns false when completedToday is zero`() {
        val habit = ApiHabit(completedToday = 0)
        assertFalse(habit.isCompletedToday)
    }
}
