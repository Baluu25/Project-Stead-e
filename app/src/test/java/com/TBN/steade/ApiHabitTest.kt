package com.TBN.steade

import com.TBN.steade.data.network.ApiHabit
import org.junit.Assert.*
import org.junit.Test

class ApiHabitTest {

    // isActive tests

    @Test
    fun `isActive returns true when isActiveRaw is true boolean`() {
        val habit = ApiHabit(isActiveRaw = true)
        assertTrue(habit.isActive)
    }

    @Test
    fun `isActive returns false when isActiveRaw is false boolean`() {
        val habit = ApiHabit(isActiveRaw = false)
        assertFalse(habit.isActive)
    }

    @Test
    fun `isActive returns true when isActiveRaw is non-zero int`() {
        val habit = ApiHabit(isActiveRaw = 1)
        assertTrue(habit.isActive)
    }

    @Test
    fun `isActive returns false when isActiveRaw is zero int`() {
        val habit = ApiHabit(isActiveRaw = 0)
        assertFalse(habit.isActive)
    }

    @Test
    fun `isActive returns true when isActiveRaw is string 1`() {
        val habit = ApiHabit(isActiveRaw = "1")
        assertTrue(habit.isActive)
    }

    @Test
    fun `isActive returns true when isActiveRaw is string true`() {
        val habit = ApiHabit(isActiveRaw = "true")
        assertTrue(habit.isActive)
    }

    @Test
    fun `isActive returns true when isActiveRaw is string TRUE uppercase`() {
        val habit = ApiHabit(isActiveRaw = "TRUE")
        assertTrue(habit.isActive)
    }

    @Test
    fun `isActive returns false when isActiveRaw is string 0`() {
        val habit = ApiHabit(isActiveRaw = "0")
        assertFalse(habit.isActive)
    }

    @Test
    fun `isActive defaults to true when isActiveRaw is null`() {
        val habit = ApiHabit(isActiveRaw = null)
        assertTrue(habit.isActive)
    }

    // isCompletedToday tests

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
