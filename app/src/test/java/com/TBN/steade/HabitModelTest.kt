package com.TBN.steade

import com.TBN.steade.data.model.Habit
import com.TBN.steade.data.model.HabitType
import org.junit.Assert.*
import org.junit.Test

class HabitModelTest {

    private fun makeHabit(
        name: String = "Exercise",
        description: String = "Daily workout",
        category: String = "Fitness",
        frequency: String = "daily",
        target: Int = 1,
        icon: String = "fa-solid fa-star",
        isActive: Boolean = true,
        isCompletedToday: Boolean = false,
        type: HabitType = HabitType.BASIC
    ) = Habit(
        name = name, description = description, category = category,
        frequency = frequency, target = target, icon = icon,
        isActive = isActive, isCompletedToday = isCompletedToday, type = type
    )

    @Test
    fun `habit defaults to isActive true`() {
        val habit = makeHabit()
        assertTrue(habit.isActive)
    }

    @Test
    fun `habit defaults to isCompletedToday false`() {
        val habit = makeHabit()
        assertFalse(habit.isCompletedToday)
    }

    @Test
    fun `habit defaults to BASIC type`() {
        val habit = makeHabit()
        assertEquals(HabitType.BASIC, habit.type)
    }

    @Test
    fun `habit stores provided name correctly`() {
        val habit = makeHabit(name = "Meditate")
        assertEquals("Meditate", habit.name)
    }

    @Test
    fun `habit stores provided category correctly`() {
        val habit = makeHabit(category = "Mindfulness")
        assertEquals("Mindfulness", habit.category)
    }

    @Test
    fun `habit can be CUSTOM type`() {
        val habit = makeHabit(type = HabitType.CUSTOM)
        assertEquals(HabitType.CUSTOM, habit.type)
    }

    @Test
    fun `two habits with same fields are equal`() {
        val h1 = makeHabit(name = "Run")
        val h2 = makeHabit(name = "Run")
        assertEquals(h1, h2)
    }

    @Test
    fun `two habits with different names are not equal`() {
        val h1 = makeHabit(name = "Run")
        val h2 = makeHabit(name = "Walk")
        assertNotEquals(h1, h2)
    }
}
