package com.TBN.steade.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.R
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.theme.SteadeNavyBlue

@Composable
fun WelcomeScreen(navController: NavController) {
    MainGradientBackground(showShadow = false) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            verticalArrangement   = Arrangement.Center,
            horizontalAlignment   = Alignment.CenterHorizontally
        ) {
            Image(
                painter            = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier           = Modifier.size(140.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text       = "STEAD-E",
                color      = Color.White,
                fontSize   = 44.sp,
                fontWeight = FontWeight.Black
            )
            Text(
                text      = "Shape your Future.\nCreate new habits.",
                color     = Color.White.copy(alpha = 0.9f),
                fontSize  = 18.sp,
                textAlign = TextAlign.Center,
                lineHeight= 26.sp
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text      = "Your all-in-one fitness partner. Track hydration,\nexercise, and health metrics together.",
                color     = Color.White.copy(alpha = 0.7f),
                fontSize  = 14.sp,
                textAlign = TextAlign.Center,
                lineHeight= 20.sp
            )
            Spacer(modifier = Modifier.height(56.dp))

            // Primary button – navy blue matching the web app
            Button(
                onClick   = { navController.navigate(Screen.Login.route) },
                modifier  = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape     = RoundedCornerShape(8.dp),
                colors    = ButtonDefaults.buttonColors(
                    containerColor = SteadeNavyBlue,
                    contentColor   = Color.White
                )
            ) {
                Text("Login", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick  = { navController.navigate(Screen.Register.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape    = RoundedCornerShape(8.dp),
                colors   = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border   = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
            ) {
                Text("Start for Free", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
