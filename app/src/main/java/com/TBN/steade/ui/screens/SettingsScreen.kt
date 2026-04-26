package com.TBN.steade.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.TBN.steade.R
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.viewmodel.SteadEViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun SettingsScreen(navController: NavController, viewModel: SteadEViewModel) {
    val context = LocalContext.current
    val prefs   = remember { context.getSharedPreferences("ModulePrefs", android.content.Context.MODE_PRIVATE) }

    var fitnessEnabled     by remember { mutableStateOf(prefs.getBoolean("fitness",     true)) }
    var mindfulnessEnabled by remember { mutableStateOf(prefs.getBoolean("mindfulness", true)) }
    var nutritionEnabled   by remember { mutableStateOf(prefs.getBoolean("nutrition",   true)) }
    var studyEnabled       by remember { mutableStateOf(prefs.getBoolean("study",       true)) }
    var workEnabled        by remember { mutableStateOf(prefs.getBoolean("work",        true)) }
    var sleepEnabled       by remember { mutableStateOf(prefs.getBoolean("sleep",       true)) }

    var masterNotifications by remember { mutableStateOf(true) }
    var popupNotifications  by remember { mutableStateOf(true) }
    var vibration           by remember { mutableStateOf(true) }

    var showEasterEgg by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        MainGradientBackground(showShadow = true) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(32.dp))

                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text("Settings", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text(
                            "💪",
                            modifier  = Modifier
                                .alpha(0.08f)
                                .padding(8.dp),
                            fontSize  = 20.sp
                        )
                    }

                    Spacer(Modifier.height(24.dp))
                    SectionHeader("Enabled Modules")
                    Spacer(Modifier.height(10.dp))

                    ModuleToggle("Fitness",     fitnessEnabled)     { fitnessEnabled     = it; prefs.edit().putBoolean("fitness",     it).apply() }
                    ModuleToggle("Mindfulness", mindfulnessEnabled) { mindfulnessEnabled = it; prefs.edit().putBoolean("mindfulness", it).apply() }
                    ModuleToggle("Nutrition",   nutritionEnabled)   { nutritionEnabled   = it; prefs.edit().putBoolean("nutrition",   it).apply() }
                    ModuleToggle("Study",       studyEnabled)       { studyEnabled       = it; prefs.edit().putBoolean("study",       it).apply() }
                    ModuleToggle("Work",        workEnabled)        { workEnabled        = it; prefs.edit().putBoolean("work",        it).apply() }
                    ModuleToggle("Sleep",       sleepEnabled)       { sleepEnabled       = it; prefs.edit().putBoolean("sleep",       it).apply() }

                    Spacer(Modifier.height(24.dp))
                    SectionHeader("Notifications")
                    Spacer(Modifier.height(10.dp))

                    ModuleToggle("Master Notifications", masterNotifications) { masterNotifications = it }
                    if (masterNotifications) {
                        ModuleToggle("Pop-up Notifications", popupNotifications) { popupNotifications = it }
                        ModuleToggle("Vibration",            vibration)           { vibration           = it }
                    }

                    Spacer(Modifier.height(24.dp))
                    SectionHeader("Easter Egg")
                    Spacer(Modifier.height(10.dp))

                    // Easter egg card
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape    = RoundedCornerShape(14.dp),
                        colors   = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
                    ) {
                        Row(
                            modifier          = Modifier
                                .fillMaxWidth()
                                .padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Weight Catcher Mini-Game 🎮", color = Color.White, fontSize = 15.sp)
                            Button(
                                onClick = { showEasterEgg = true },
                                colors  = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor   = SteadeNavyBlue
                                ),
                                shape   = RoundedCornerShape(8.dp)
                            ) { Text("Play", fontWeight = FontWeight.Bold) }
                        }
                    }

                    Spacer(Modifier.height(28.dp))

                    Text(
                        "Note: Certain data and advanced account settings can only be adjusted or modified on the official website.",
                        color     = Color.White.copy(alpha = 0.55f),
                        fontSize  = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Button(
                        onClick  = {
                            viewModel.logout {
                                navController.navigate(Screen.Welcome.route) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape    = RoundedCornerShape(10.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.75f),
                            contentColor   = Color.White
                        )
                    ) {
                        Text("Logout", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(Modifier.height(24.dp))
                }
                BottomNavBar(navController)
            }
        }

        if (showEasterEgg) {
            Box(modifier = Modifier.fillMaxSize().zIndex(10f)) {
                StackGame(onClose = { showEasterEgg = false })
            }
        }
    }
}

@Composable
fun SectionHeader(title: String) {
    Text(
        title,
        color      = Color.White,
        fontSize   = 17.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun ModuleToggle(label: String, isEnabled: Boolean, onToggle: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color.White, fontSize = 15.sp)
            Switch(
                checked        = isEnabled,
                onCheckedChange= onToggle,
                colors         = SwitchDefaults.colors(
                    checkedThumbColor   = Color.White,
                    checkedTrackColor   = Color.Green.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.LightGray,
                    uncheckedTrackColor = Color.Gray.copy(alpha = 0.4f)
                )
            )
        }
    }
}

@Composable
fun StackGame(onClose: () -> Unit) {
    var score           by remember { mutableStateOf(0) }
    var gameOver        by remember { mutableStateOf(false) }
    var weightPositionX by remember { mutableStateOf(Random.nextFloat()) }
    var catcherPosition by remember { mutableStateOf(0.5f) }
    var fallingY        by remember { mutableStateOf(0f) }

    val speed = (0.003f + (score / 50) * 0.001f).coerceAtMost(0.012f)

    LaunchedEffect(gameOver) {
        while (!gameOver) {
            delay(16)
            fallingY += speed
            if (fallingY >= 0.85f) {
                val hit = kotlin.math.abs(weightPositionX - catcherPosition) < 0.18f
                if (hit) {
                    score         += 10
                    fallingY       = 0f
                    weightPositionX= Random.nextFloat()
                } else if (fallingY >= 1.05f) {
                    gameOver = true
                }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.96f))
            .pointerInput(Unit) {
                detectDragGestures { change, drag ->
                    change.consume()
                    catcherPosition = (catcherPosition + drag.x / size.width * 1.3f).coerceIn(0.05f, 0.95f)
                }
            }
            .padding(24.dp)
    ) {
        if (!gameOver) {
            Text(
                "Weight Lifted: ${score}kg",
                color      = Color.White,
                fontSize   = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier   = Modifier.align(Alignment.TopCenter)
            )
            Image(
                painter            = painterResource(id = R.drawable.dumbell),
                contentDescription = "Weight",
                modifier           = Modifier
                    .size(58.dp)
                    .align(Alignment.TopStart)
                    .offset(x = (weightPositionX * 280).dp, y = (fallingY * 600).dp)
            )
            Image(
                painter            = painterResource(id = R.drawable.eastereggg),
                contentDescription = "Character",
                modifier           = Modifier
                    .size(110.dp)
                    .align(Alignment.BottomStart)
                    .offset(x = (catcherPosition * 280 - 55).dp, y = (-16).dp)
            )
            Text(
                "Drag left/right to catch!",
                color    = Color.White.copy(alpha = 0.45f),
                fontSize = 13.sp,
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 130.dp)
            )
        } else {
            Column(
                modifier            = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("GAME OVER", color = Color.Red, fontSize = 44.sp, fontWeight = FontWeight.ExtraBold)
                Text("You lifted: ${score}kg", color = Color.White, fontSize = 22.sp)
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = onClose,
                    colors  = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black),
                    shape   = RoundedCornerShape(10.dp)
                ) { Text("Back to Settings", fontWeight = FontWeight.Bold) }
            }
        }
    }
}
