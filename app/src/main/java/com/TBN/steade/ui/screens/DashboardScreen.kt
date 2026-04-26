package com.TBN.steade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import androidx.compose.ui.graphics.vector.ImageVector
import com.TBN.steade.ui.components.habitIconToMaterialIcon
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.viewmodel.SteadEViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.random.Random

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: SteadEViewModel) {
    val context = LocalContext.current
    val modulePrefs = remember { context.getSharedPreferences("ModulePrefs", android.content.Context.MODE_PRIVATE) }

    val fitnessEnabled     = modulePrefs.getBoolean("fitness", true)
    val mindfulnessEnabled = modulePrefs.getBoolean("mindfulness", true)
    val nutritionEnabled   = modulePrefs.getBoolean("nutrition", true)
    val studyEnabled       = modulePrefs.getBoolean("study", true)
    val workEnabled        = modulePrefs.getBoolean("work", true)
    val sleepEnabled       = modulePrefs.getBoolean("sleep", true)

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val localCompletions = remember { mutableStateMapOf<String, Boolean>() }

    val apiHabits = viewModel.habits
    val today = LocalDate.now()

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(32.dp))

                // Header
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            "Hello, ${viewModel.getDisplayName()}!",
                            color      = Color.White,
                            fontSize   = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Ready to build better habits?",
                            color    = Color.White.copy(alpha = 0.7f),
                            fontSize = 13.sp
                        )
                    }
                    IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile", tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Weekly date strip
                WeeklyDateStrip(selectedDate = selectedDate, onDateSelected = { selectedDate = it })

                Spacer(Modifier.height(20.dp))

                // Streak card
                DashboardStreakCard(streak = viewModel.statistics?.currentStreak ?: 0)

                Spacer(Modifier.height(20.dp))

                // Sleep suggestion card
                if (sleepEnabled) {
                    SleepSuggestionCard()
                    Spacer(Modifier.height(20.dp))
                }

                // Habits for selected date
                val isPast   = selectedDate.isBefore(today)
                val isFuture = selectedDate.isAfter(today)
                val titleStr = when {
                    selectedDate == today -> "Today's Habits"
                    isPast               -> "Habits on ${selectedDate.format(DateTimeFormatter.ofPattern("MMM dd"))}"
                    else                 -> "Planned for ${selectedDate.format(DateTimeFormatter.ofPattern("MMM dd"))}"
                }

                Text(titleStr, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))

                if (viewModel.habitsLoading) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                } else if (apiHabits.isEmpty()) {
                    // fallback to module-based list when no API data
                    val fallbackHabits = buildList {
                        if (fitnessEnabled)     add("Fitness 🏋️")
                        if (mindfulnessEnabled) add("Mindfulness 🧘")
                        if (nutritionEnabled)   add("Nutrition 🍎")
                        if (studyEnabled)       add("Study 📖")
                        if (workEnabled)        add("Work 💻")
                        if (sleepEnabled)       add("Sleep 🌙")
                    }
                    fallbackHabits.forEach { h ->
                        val done = if (isPast) (h.length + selectedDate.dayOfMonth) % 2 == 0
                                   else if (selectedDate == today) localCompletions[h] ?: false
                                   else false
                        DashHabitItem(
                            name      = h,
                            icon      = Icons.Default.Star,
                            isPast    = isPast,
                            isFuture  = isFuture,
                            isDone    = done,
                            onToggle  = { localCompletions[h] = it }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                } else {
                    apiHabits.filter { it.isActive }.forEach { habit ->
                        val done = if (isPast) (habit.name.length + selectedDate.dayOfMonth) % 2 == 0
                                   else if (selectedDate == today) localCompletions[habit.name] ?: habit.isCompletedToday
                                   else false
                        DashHabitItem(
                            name     = habit.name,
                            icon     = habitIconToMaterialIcon(habit.icon),
                            isPast   = isPast,
                            isFuture = isFuture,
                            isDone   = done,
                            onToggle = { checked -> localCompletions[habit.name] = checked }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }

                Spacer(Modifier.height(16.dp))
            }
            BottomNavBar(navController)
        }
    }
}

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun WeeklyDateStrip(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val today = LocalDate.now()
    // Show the week containing today, centred
    val monday = today.with(DayOfWeek.MONDAY)

    Column {
        Text("Calendar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))

        val pagerState = rememberPagerState(pageCount = { 52 }, initialPage = 26)
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
            val weekStart = monday.plusWeeks((page - 26).toLong())
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { idx, dayLabel ->
                    val date       = weekStart.plusDays(idx.toLong())
                    val isSelected = date == selectedDate
                    val isToday    = date == today
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                when {
                                    isSelected -> Color.White.copy(alpha = 0.3f)
                                    isToday    -> Color.White.copy(alpha = 0.12f)
                                    else       -> Color.Transparent
                                }
                            )
                            .clickable { onDateSelected(date) }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                    ) {
                        Text(dayLabel, color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            date.dayOfMonth.toString(),
                            color      = Color.White,
                            fontSize   = 15.sp,
                            fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardStreakCard(streak: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("🔥", fontSize = 34.sp)
            Spacer(Modifier.width(14.dp))
            Column {
                Text("Current Streak", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(
                    "$streak day${if (streak != 1) "s" else ""}",
                    color    = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    listOf("M","T","W","T","F","S","S").forEach { d ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(d, color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp)
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(if (Random.nextBoolean()) Color.Green else Color.White.copy(alpha = 0.2f))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SleepSuggestionCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = Color.Blue.copy(alpha = 0.18f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("🌙", fontSize = 30.sp)
            Spacer(Modifier.width(14.dp))
            Column {
                Text("Optimal Sleep Schedule", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text("Suggested: 22:30 – 06:30", color = Color.White.copy(alpha = 0.8f), fontSize = 13.sp)
                Text("Duration: 8 hours", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun DashHabitItem(
    name    : String,
    icon    : ImageVector,
    isPast  : Boolean,
    isFuture: Boolean,
    isDone  : Boolean,
    onToggle: (Boolean) -> Unit
) {
    val alpha = if (isPast && !isDone) 0.45f else 1f
    Card(
        modifier = Modifier.fillMaxWidth().alpha(alpha),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.13f))
    ) {
        Row(
            modifier              = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.85f), modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(name, color = Color.White, fontSize = 15.sp, modifier = Modifier.weight(1f))
            when {
                isPast  -> Icon(
                    if (isDone) Icons.Default.Check else Icons.Default.Close,
                    contentDescription = null,
                    tint = if (isDone) Color.Green else Color.Red.copy(alpha = 0.7f)
                )
                isFuture -> Icon(Icons.Default.Lock, contentDescription = null, tint = Color.White.copy(alpha = 0.35f), modifier = Modifier.size(18.dp))
                else     -> Checkbox(
                    checked         = isDone,
                    onCheckedChange = onToggle,
                    colors          = CheckboxDefaults.colors(
                        uncheckedColor = Color.White,
                        checkedColor   = Color.White,
                        checkmarkColor = Color.Black
                    )
                )
            }
        }
    }
}
