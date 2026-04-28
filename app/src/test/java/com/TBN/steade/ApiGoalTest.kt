package com.TBN.steade

import com.TBN.steade.data.network.ApiGoal
import org.junit.Assert.*
import org.junit.Test

class ApiGoalTest {

    // progress tests

    @Test
    fun `progress returns correct ratio when current is half of target`() {
        val goal = ApiGoal(targetValue = 10f, currentValue = 5f)
        assertEquals(0.5f, goal.progress, 0.001f)
    }

    @Test
    fun `progress returns 1 when current equals target`() {
        val goal = ApiGoal(targetValue = 10f, currentValue = 10f)
        assertEquals(1f, goal.progress, 0.001f)
    }

    @Test
    fun `progress is capped at 1 when current exceeds target`() {
        val goal = ApiGoal(targetValue = 10f, currentValue = 15f)
        assertEquals(1f, goal.progress, 0.001f)
    }

    @Test
    fun `progress returns 0 when current is zero`() {
        val goal = ApiGoal(targetValue = 10f, currentValue = 0f)
        assertEquals(0f, goal.progress, 0.001f)
    }

    @Test
    fun `progress returns 0 when targetValue is zero to avoid division by zero`() {
        val goal = ApiGoal(targetValue = 0f, currentValue = 5f)
        assertEquals(0f, goal.progress, 0.001f)
    }

    // displayName tests

    @Test
    fun `displayName returns title when title is not null`() {
        val goal = ApiGoal(name = "run", title = "Run a marathon")
        assertEquals("Run a marathon", goal.displayName)
    }

    @Test
    fun `displayName falls back to name when title is null`() {
        val goal = ApiGoal(name = "run", title = null)
        assertEquals("run", goal.displayName)
    }
}
