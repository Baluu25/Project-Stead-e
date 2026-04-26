package com.TBN.steade.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import kotlin.math.abs
import kotlin.random.Random

@Composable
fun SettingsScreen(navController: NavController, viewModel: SteadEViewModel) {
    val context = LocalContext.current
    val prefs   = remember { context.getSharedPreferences("SteadESettings", android.content.Context.MODE_PRIVATE) }

    // ── Habit category toggles ──────────────────────────────────────────────
    var fitnessEnabled     by remember { mutableStateOf(prefs.getBoolean("cat_fitness",     true)) }
    var nutritionEnabled   by remember { mutableStateOf(prefs.getBoolean("cat_nutrition",   true)) }
    var mindfulnessEnabled by remember { mutableStateOf(prefs.getBoolean("cat_mindfulness", true)) }
    var studyEnabled       by remember { mutableStateOf(prefs.getBoolean("cat_study",       true)) }
    var workEnabled        by remember { mutableStateOf(prefs.getBoolean("cat_work",        true)) }

    // ── UI state ────────────────────────────────────────────────────────────
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showEasterEgg    by remember { mutableStateOf(false) }
    var copyrightTaps    by remember { mutableStateOf(0) }
    var refreshed        by remember { mutableStateOf(false) }

    // Clear "Synced ✓" badge after 2 s
    LaunchedEffect(refreshed) {
        if (refreshed) { delay(2_000); refreshed = false }
    }

    // Reset tap counter after 3 s of inactivity so partial taps don’t linger
    LaunchedEffect(copyrightTaps) {
        if (copyrightTaps in 1..4) { delay(3_000); copyrightTaps = 0 }
    }

    // ── Logout confirmation ─────────────────────────────────────────────────
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title  = { Text("Log Out", fontWeight = FontWeight.Bold) },
            text   = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = {
                    showLogoutDialog = false
                    viewModel.logout {
                        navController.navigate(Screen.Welcome.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                }) {
                    Text("Log Out", color = Color.Red, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) { Text("Cancel") }
            }
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        MainGradientBackground(showShadow = true) {
            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(32.dp))
                    Text(
                        "Settings",
                        color      = Color.White,
                        fontSize   = 28.sp,
                        fontWeight = FontWeight.Bold
                    )

                    // ── Habit Categories ────────────────────────────────────
                    Spacer(Modifier.height(28.dp))
                    SettingsSectionHeader("Habit Categories")
                    Spacer(Modifier.height(10.dp))

                    CategoryToggle("Fitness",     fitnessEnabled)     { v -> fitnessEnabled     = v; prefs.edit().putBoolean("cat_fitness",     v).apply() }
                    CategoryToggle("Nutrition",   nutritionEnabled)   { v -> nutritionEnabled   = v; prefs.edit().putBoolean("cat_nutrition",   v).apply() }
                    CategoryToggle("Mindfulness", mindfulnessEnabled) { v -> mindfulnessEnabled = v; prefs.edit().putBoolean("cat_mindfulness", v).apply() }
                    CategoryToggle("Study",       studyEnabled)       { v -> studyEnabled       = v; prefs.edit().putBoolean("cat_study",       v).apply() }
                    CategoryToggle("Work",        workEnabled)        { v -> workEnabled        = v; prefs.edit().putBoolean("cat_work",        v).apply() }

                    // ── Profile & Data ──────────────────────────────────────
                    Spacer(Modifier.height(28.dp))
                    SettingsSectionHeader("Profile & Data")
                    Spacer(Modifier.height(10.dp))

                    SettingsActionRow(
                        label   = "View Profile",
                        icon    = Icons.Default.Person,
                        onClick = { navController.navigate(Screen.Profile.route) }
                    )
                    Spacer(Modifier.height(8.dp))
                    SettingsActionRow(
                        label   = if (refreshed) "Synced ✓" else "Refresh All Data",
                        icon    = if (refreshed) Icons.Default.Check else Icons.Default.Refresh,
                        tint    = if (refreshed) Color(0xFF66FF99) else Color.White,
                        onClick = {
                            viewModel.loadUser()
                            viewModel.loadHabits()
                            viewModel.loadGoals()
                            viewModel.loadStatistics()
                            viewModel.loadAchievements()
                            refreshed = true
                        }
                    )

                    // ── Copyright + hidden easter-egg trigger ───────────────
                    Spacer(Modifier.height(40.dp))
                    Text(
                        "© 2025 SteadE · All rights reserved",
                        color     = Color.White.copy(alpha = 0.35f),
                        fontSize  = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier
                            .fillMaxWidth()
                            .clickable {
                                copyrightTaps++
                                if (copyrightTaps >= 5) {
                                    copyrightTaps = 0
                                    showEasterEgg = true
                                }
                            }
                            .padding(vertical = 10.dp)
                    )

                    // ── Log Out ─────────────────────────────────────────────
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick  = { showLogoutDialog = true },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape    = RoundedCornerShape(10.dp),
                        colors   = ButtonDefaults.buttonColors(
                            containerColor = Color.Red.copy(alpha = 0.75f),
                            contentColor   = Color.White
                        )
                    ) {
                        Text("Log Out", fontSize = 17.sp, fontWeight = FontWeight.SemiBold)
                    }

                    Spacer(Modifier.height(24.dp))
                }

                BottomNavBar(navController)
            }
        }

        // Easter egg overlay (full-screen, above everything)
        if (showEasterEgg) {
            Box(modifier = Modifier.fillMaxSize().zIndex(10f)) {
                StackGame(onClose = { showEasterEgg = false })
            }
        }
    }
}

// ── Shared section header ───────────────────────────────────────────────────
@Composable
fun SettingsSectionHeader(title: String) {
    Text(
        title,
        color      = Color.White,
        fontSize   = 17.sp,
        fontWeight = FontWeight.SemiBold
    )
}

// ── Category toggle row ─────────────────────────────────────────────────────
@Composable
fun CategoryToggle(label: String, isEnabled: Boolean, onToggle: (Boolean) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        shape    = RoundedCornerShape(12.dp),
        border   = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        colors   = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(label, color = Color.White, fontSize = 15.sp)
            Switch(
                checked         = isEnabled,
                onCheckedChange = onToggle,
                colors          = SwitchDefaults.colors(
                    checkedThumbColor   = Color.White,
                    checkedTrackColor   = Color.Green.copy(alpha = 0.5f),
                    uncheckedThumbColor = Color.LightGray,
                    uncheckedTrackColor = Color.Gray.copy(alpha = 0.4f)
                )
            )
        }
    }
}

// ── Tappable action row ─────────────────────────────────────────────────────
@Composable
fun SettingsActionRow(
    label  : String,
    icon   : ImageVector,
    tint   : Color = Color.White,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape   = RoundedCornerShape(12.dp),
        border  = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        colors  = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
                Text(label, color = tint, fontSize = 15.sp)
            }
            Icon(
                Icons.Default.ChevronRight,
                contentDescription = null,
                tint     = tint.copy(alpha = 0.45f),
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

// ── Easter-egg mini-game ────────────────────────────────────────────────────
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
                val hit = abs(weightPositionX - catcherPosition) < 0.18f
                if (hit) {
                    score           += 10
                    fallingY         = 0f
                    weightPositionX  = Random.nextFloat()
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
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 130.dp)
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
