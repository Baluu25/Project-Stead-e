package com.TBN.steade.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.R
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.components.DarkGradientBrush
import com.TBN.steade.ui.viewmodel.SteadEViewModel
import kotlinx.coroutines.delay

@Composable
fun LoadingScreen(navController: NavController, viewModel: SteadEViewModel) {
    LaunchedEffect(Unit) {
        delay(2000)
        if (viewModel.isLoggedIn) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.Loading.route) { inclusive = true }
            }
        } else {
            navController.navigate(Screen.Welcome.route) {
                popUpTo(Screen.Loading.route) { inclusive = true }
            }
        }
    }

    // Darker blue gradient background matching the Laravel project's deeper navy tone
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = DarkGradientBrush)
    ) {
        Column(
            modifier              = Modifier.fillMaxSize(),
            verticalArrangement   = Arrangement.Center,
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {
            Image(
                painter            = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier           = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text       = "STEAD-E",
                color      = Color.White,
                fontSize   = 32.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text     = "Build Better Habits",
                color    = Color.White.copy(alpha = 0.8f),
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(48.dp))
            CircularProgressIndicator(
                color       = Color.White,
                strokeWidth = 3.dp,
                modifier    = Modifier.size(40.dp)
            )
        }
    }
}

