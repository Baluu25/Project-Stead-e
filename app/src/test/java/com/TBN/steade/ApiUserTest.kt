package com.TBN.steade

import com.TBN.steade.data.network.ApiUser
import org.junit.Assert.*
import org.junit.Test

class ApiUserTest {

    @Test
    fun `preferredCategoriesText returns joined string when value is a list`() {
        val user = ApiUser(preferredCategories = listOf("Fitness", "Sleep", "Nutrition"))
        assertEquals("Fitness, Sleep, Nutrition", user.preferredCategoriesText())
    }

    @Test
    fun `preferredCategoriesText returns string as-is when value is already a string`() {
        val user = ApiUser(preferredCategories = "Fitness")
        assertEquals("Fitness", user.preferredCategoriesText())
    }

    @Test
    fun `preferredCategoriesText returns null when value is null`() {
        val user = ApiUser(preferredCategories = null)
        assertNull(user.preferredCategoriesText())
    }

    @Test
    fun `preferredCategoriesText returns null for unexpected type`() {
        val user = ApiUser(preferredCategories = 42)
        assertNull(user.preferredCategoriesText())
    }
}
