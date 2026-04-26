package com.TBN.steade.ui.navigation

sealed class Screen(val route: String) {
    object Loading : Screen("loading")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object Habits : Screen("habits")
    object Goals : Screen("goals")
    object Statistics : Screen("statistics")
    object Achievements : Screen("achievements")
    object Profile : Screen("profile")
    object Settings : Screen("settings")
}
