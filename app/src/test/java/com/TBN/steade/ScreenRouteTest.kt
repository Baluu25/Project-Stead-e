package com.TBN.steade

import com.TBN.steade.ui.navigation.Screen
import org.junit.Assert.*
import org.junit.Test

class ScreenRouteTest {

    @Test
    fun `Loading screen has correct route`() {
        assertEquals("loading", Screen.Loading.route)
    }

    @Test
    fun `Welcome screen has correct route`() {
        assertEquals("welcome", Screen.Welcome.route)
    }

    @Test
    fun `Login screen has correct route`() {
        assertEquals("login", Screen.Login.route)
    }

    @Test
    fun `Register screen has correct route`() {
        assertEquals("register", Screen.Register.route)
    }

    @Test
    fun `Dashboard screen has correct route`() {
        assertEquals("dashboard", Screen.Dashboard.route)
    }

    @Test
    fun `Habits screen has correct route`() {
        assertEquals("habits", Screen.Habits.route)
    }

    @Test
    fun `Goals screen has correct route`() {
        assertEquals("goals", Screen.Goals.route)
    }

    @Test
    fun `Statistics screen has correct route`() {
        assertEquals("statistics", Screen.Statistics.route)
    }

    @Test
    fun `Achievements screen has correct route`() {
        assertEquals("achievements", Screen.Achievements.route)
    }

    @Test
    fun `Profile screen has correct route`() {
        assertEquals("profile", Screen.Profile.route)
    }

    @Test
    fun `Settings screen has correct route`() {
        assertEquals("settings", Screen.Settings.route)
    }

    @Test
    fun `all screen routes are unique`() {
        val routes = listOf(
            Screen.Loading.route, Screen.Welcome.route, Screen.Login.route,
            Screen.Register.route, Screen.Dashboard.route, Screen.Habits.route,
            Screen.Goals.route, Screen.Statistics.route, Screen.Achievements.route,
            Screen.Profile.route, Screen.Settings.route
        )
        assertEquals(routes.size, routes.toSet().size)
    }
}
