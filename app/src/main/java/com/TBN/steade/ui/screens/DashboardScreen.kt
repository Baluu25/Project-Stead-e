package com.TBN.steade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.components.habitIconToMaterialIcon
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.theme.SteadeRed
import com.TBN.steade.ui.viewmodel.SteadEViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: SteadEViewModel) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    // habitId -> completedToday count (optimistic local state)
    val localCompletions = remember { mutableStateMapOf<Int, Int>() }

    val apiHabits = viewModel.habits
    val today     = LocalDate.now()
    val englishMd = DateTimeFormatter.ofPattern("MMM dd", Locale.ENGLISH)

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
                        Icon(
                            Icons.Default.Person,
                            contentDescription = "Profile",
                            tint     = Color.White,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                WeeklyDateStrip(selectedDate = selectedDate, onDateSelected = { selectedDate = it })

                Spacer(Modifier.height(20.dp))

                // Streak card — recomposes automatically when viewModel.statistics updates
                DashboardStreakCard(
                    streak   = viewModel.statistics?.currentStreak ?: 0,
                    dailyMap = viewModel.statistics?.dailyCompletions ?: emptyMap()
                )

                Spacer(Modifier.height(20.dp))

                val isPast   = selectedDate.isBefore(today)
                val isFuture = selectedDate.isAfter(today)
                val titleStr = when {
                    selectedDate == today -> "Today's Habits"
                    isPast               -> "Habits on ${selectedDate.format(englishMd)}"
                    else                 -> "Planned for ${selectedDate.format(englishMd)}"
                }

                Text(titleStr, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(10.dp))

                if (viewModel.habitsLoading) {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = Color.White)
                    }
                } else if (apiHabits.isEmpty()) {
                    Text(
                        "No habits yet. Add habits from the Habits tab.",
                        color     = Color.White.copy(alpha = 0.7f),
                        fontSize  = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier  = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                    )
                } else {
                    apiHabits.filter { it.isActive }.forEach { habit ->
                        val target    = habit.targetCount ?: 1
                        val completed = localCompletions.getOrElse(habit.id) { habit.completedToday }

                        DashHabitItem(
                            name      = habit.name,
                            icon      = habitIconToMaterialIcon(habit.icon),
                            unit      = habit.unit ?: "",
                            completed = completed,
                            target    = target,
                            isPast    = isPast,
                            isFuture  = isFuture,
                            onAdd     = { amount ->
                                localCompletions[habit.id] = completed + amount
                                viewModel.logHabitCompletion(habit.id, amount) { serverCount ->
                                    localCompletions[habit.id] = serverCount
                                }
                            },
                            onRemove  = { amount ->
                                val actual = minOf(amount, completed)
                                if (actual > 0) {
                                    localCompletions[habit.id] = completed - actual
                                    viewModel.removeHabitCompletion(habit.id, actual) { serverCount ->
                                        localCompletions[habit.id] = serverCount
                                    }
                                }
                            }
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

// ─── Weekly date strip ────────────────────────────────────────────────────────

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun WeeklyDateStrip(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val today  = LocalDate.now()
    val monday = today.with(DayOfWeek.MONDAY)
    // Fixed English single-letter day headers for the strip
    val dayLetters = listOf("M", "T", "W", "T", "F", "S", "S")

    Column {
        Text("Calendar", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(8.dp))

        val pagerState = rememberPagerState(pageCount = { 52 }, initialPage = 26)
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxWidth()) { page ->
            val weekStart = monday.plusWeeks((page - 26).toLong())
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                dayLetters.forEachIndexed { idx, letter ->
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
                        Text(letter, color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp)
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

// ─── Streak card — matches Laravel's streak-aside + streak-week layout ────────

@Composable
fun DashboardStreakCard(streak: Int, dailyMap: Map<String, Int>) {
    val fmt   = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()
    val last7 = (6 downTo 0).map { today.minusDays(it.toLong()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)) {
            // Top: flame icon + streak number
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Default.LocalFireDepartment,
                    contentDescription = "Streak",
                    tint     = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(Modifier.width(10.dp))
                Column {
                    Text("Current Streak", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "$streak day${if (streak != 1) "s" else ""}",
                        color    = Color.White.copy(alpha = 0.75f),
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            // Bottom: 7-day circles matching Laravel's day-circle design
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                last7.forEach { date ->
                    val done    = (dailyMap[date.format(fmt)] ?: 0) > 0
                    val isToday = date == today
                    // 2-letter English day label: Mon→Mo, Tue→Tu, etc.
                    val label   = date.dayOfWeek
                        .getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH)
                        .take(2)

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Active: SteadeNavyBlue (#25408F) matching .day-circle.active
                        // Inactive: rgba(255,255,255,0.1) matching .day-circle
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        done    -> SteadeNavyBlue
                                        isToday -> Color.White.copy(alpha = 0.18f)
                                        else    -> Color.White.copy(alpha = 0.1f)
                                    }
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (done) {
                                Icon(
                                    Icons.Default.Check,
                                    contentDescription = null,
                                    tint     = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                        Spacer(Modifier.height(5.dp))
                        Text(
                            label,
                            color    = Color.White.copy(alpha = if (isToday) 0.9f else 0.55f),
                            fontSize = 10.sp,
                            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}

// ─── Habit row — matches Laravel's habit-item layout ─────────────────────────

@Composable
fun DashHabitItem(
    name     : String,
    icon     : ImageVector,
    unit     : String,
    completed: Int,
    target   : Int,
    isPast   : Boolean,
    isFuture : Boolean,
    onAdd    : (Int) -> Unit,
    onRemove : (Int) -> Unit
) {
    val isDone   = completed >= target
    val progress = (completed.toFloat() / target.coerceAtLeast(1)).coerceIn(0f, 1f)

    // Step value state — local to this item, resets when item leaves composition
    var stepText by remember { mutableStateOf("1") }
    val step = stepText.toIntOrNull()?.coerceAtLeast(1) ?: 1

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        // Completed card: rgba(37,64,143,0.3) matching .habit-item.completed
        colors   = CardDefaults.cardColors(
            containerColor = if (isDone) SteadeNavyBlue.copy(alpha = 0.3f)
                             else        Color.White.copy(alpha = 0.13f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier              = Modifier.fillMaxWidth()
            ) {
                // Habit icon + name + progress counter
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                            .background(
                                if (isDone) SteadeNavyBlue.copy(alpha = 0.45f)
                                else        Color.White.copy(alpha = 0.15f)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            tint     = Color.White.copy(alpha = if (isDone) 1f else 0.85f),
                            modifier = Modifier.size(17.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Column {
                        Text(name, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                        Text(
                            "$completed / $target${if (unit.isNotBlank()) " $unit" else ""}",
                            color    = Color.White.copy(alpha = 0.6f),
                            fontSize = 12.sp
                        )
                    }
                }

                Spacer(Modifier.width(6.dp))

                // Right-side action area — matches Laravel exactly:
                // past → check/x, future → lock, done → badge, else → − input +
                when {
                    isPast -> Icon(
                        if (isDone) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = null,
                        tint     = if (isDone) Color.White else Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp)
                    )

                    isFuture -> Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint     = Color.White.copy(alpha = 0.35f),
                        modifier = Modifier.size(18.dp)
                    )

                    // Done badge — matches Laravel's habit-done-badge (white icon, no fill)
                    isDone -> Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Done",
                            tint     = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            "Done",
                            color      = Color.White,
                            fontSize   = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Active controls: − [step] + (matches Laravel's habit-actions)
                    else -> Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // − button: SteadeNavyBlue (#25408F) matching .btn-remove-progress
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(
                                    if (completed > 0) SteadeNavyBlue
                                    else SteadeNavyBlue.copy(alpha = 0.35f)
                                )
                                .clickable(enabled = completed > 0) { onRemove(step) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint     = Color.White.copy(alpha = if (completed > 0) 1f else 0.4f),
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        // Editable step value
                        Box(
                            modifier = Modifier
                                .width(36.dp)
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .padding(horizontal = 4.dp, vertical = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            BasicTextField(
                                value          = stepText,
                                onValueChange  = { v ->
                                    if (v.isEmpty() || (v.length <= 3 && v.all(Char::isDigit)))
                                        stepText = v
                                },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                singleLine      = true,
                                textStyle       = TextStyle(
                                    color      = Color.White,
                                    fontSize   = 13.sp,
                                    textAlign  = TextAlign.Center,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }

                        // + button: SteadeNavyBlue (#25408F) matching .btn-add-progress
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(SteadeNavyBlue)
                                .clickable { onAdd(step) },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = "Increase",
                                tint     = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            // Progress bar — shown for today and past dates
            if (!isFuture) {
                Spacer(Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color.White.copy(alpha = 0.15f))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(progress)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(2.dp))
                            // Progress fill: SteadeRed (#FF2A00) matching .progress-bar-fill
                            .background(SteadeRed)
                    )
                }
            }
        }
    }
}
