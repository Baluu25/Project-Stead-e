package com.TBN.steade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.screens.*
import com.TBN.steade.ui.theme.SteadEDoneTheme
import com.TBN.steade.ui.viewmodel.SteadEViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SteadEDoneTheme {
                SteadEApp()
            }
        }
    }
}

@Composable
fun SteadEApp() {
    val navController = rememberNavController()
    val viewModel: SteadEViewModel = viewModel()

    NavHost(
        navController    = navController,
        startDestination = Screen.Loading.route
    ) {
        composable(Screen.Loading.route)      { LoadingScreen(navController, viewModel) }
        composable(Screen.Welcome.route)      { WelcomeScreen(navController) }
        composable(Screen.Login.route)        { LoginScreen(navController, viewModel) }
        composable(Screen.Register.route)     { RegisterScreen(navController, viewModel) }
        composable(Screen.Dashboard.route)    { DashboardScreen(navController, viewModel) }
        composable(Screen.Habits.route)       { HabitsScreen(navController, viewModel) }
        composable(Screen.Goals.route)        { GoalsScreen(navController, viewModel) }
        composable(Screen.Statistics.route)   { StatisticsScreen(navController, viewModel) }
        composable(Screen.Achievements.route) { AchievementsScreen(navController, viewModel) }
        composable(Screen.Profile.route)      { ProfileScreen(navController, viewModel) }
        composable(Screen.Settings.route)     { SettingsScreen(navController, viewModel) }
    }
}
