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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.TBN.steade.ui.viewmodel.SteadEViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(navController: NavController, viewModel: SteadEViewModel) {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    // habitId -> completedToday count (optimistic local state)
    val localCompletions = remember { mutableStateMapOf<Int, Int>() }

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

                // Streak card — refreshes automatically when viewModel.statistics updates
                DashboardStreakCard(
                    streak   = viewModel.statistics?.currentStreak ?: 0,
                    dailyMap = viewModel.statistics?.dailyCompletions ?: emptyMap()
                )

                Spacer(Modifier.height(20.dp))

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
                        // Use local optimistic state; fall back to API value on first access
                        val completed = localCompletions.getOrElse(habit.id) { habit.completedToday }

                        DashHabitItem(
                            name      = habit.name,
                            icon      = habitIconToMaterialIcon(habit.icon),
                            unit      = habit.unit ?: "",
                            completed = completed,
                            target    = target,
                            isPast    = isPast,
                            isFuture  = isFuture,
                            onAdd     = {
                                localCompletions[habit.id] = completed + 1
                                viewModel.logHabitCompletion(habit.id) { serverCount ->
                                    localCompletions[habit.id] = serverCount
                                }
                            },
                            onRemove  = {
                                if (completed > 0) {
                                    localCompletions[habit.id] = completed - 1
                                    viewModel.removeHabitCompletion(habit.id) { serverCount ->
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

@OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)
@Composable
fun WeeklyDateStrip(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val today  = LocalDate.now()
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
fun DashboardStreakCard(streak: Int, dailyMap: Map<String, Int>) {
    val fmt   = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()
    // Last 7 days ending today
    val last7 = (6 downTo 0).map { today.minusDays(it.toLong()) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("🔥", fontSize = 34.sp)
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Current Streak", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(
                    "$streak day${if (streak != 1) "s" else ""}",
                    color    = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
            // Last-7-days dots — today dot turns green once a completion is logged and stats reload
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                last7.forEach { date ->
                    val hasCompletion = (dailyMap[date.format(fmt)] ?: 0) > 0
                    val isToday       = date == today
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text     = date.dayOfWeek.getDisplayName(
                                java.time.format.TextStyle.NARROW,
                                java.util.Locale.getDefault()
                            ),
                            color    = Color.White.copy(alpha = 0.5f),
                            fontSize = 9.sp
                        )
                        Spacer(Modifier.height(3.dp))
                        Box(
                            modifier = Modifier
                                .size(if (isToday) 11.dp else 9.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        hasCompletion -> Color(0xFF4CAF50)  // green when done
                                        isToday       -> Color.White.copy(alpha = 0.4f)  // highlighted today
                                        else          -> Color.White.copy(alpha = 0.2f)
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DashHabitItem(
    name     : String,
    icon     : ImageVector,
    unit     : String,
    completed: Int,
    target   : Int,
    isPast   : Boolean,
    isFuture : Boolean,
    onAdd    : () -> Unit,
    onRemove : () -> Unit
) {
    val isDone   = completed >= target
    val progress = (completed.toFloat() / target.coerceAtLeast(1)).coerceIn(0f, 1f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(12.dp),
        colors   = CardDefaults.cardColors(
            containerColor = if (isDone) Color.White.copy(alpha = 0.22f) else Color.White.copy(alpha = 0.13f)
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
                // Icon + name + count
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = if (isDone) 0.3f else 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            icon,
                            contentDescription = null,
                            tint     = if (isDone) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.85f),
                            modifier = Modifier.size(16.dp)
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

                Spacer(Modifier.width(8.dp))

                // Action controls on the right
                when {
                    isPast -> Icon(
                        if (isDone) Icons.Default.Check else Icons.Default.Close,
                        contentDescription = null,
                        tint     = if (isDone) Color(0xFF4CAF50) else Color.Red.copy(alpha = 0.7f),
                        modifier = Modifier.size(20.dp)
                    )
                    isFuture -> Icon(
                        Icons.Default.Lock,
                        contentDescription = null,
                        tint     = Color.White.copy(alpha = 0.35f),
                        modifier = Modifier.size(18.dp)
                    )
                    isDone -> Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF4CAF50).copy(alpha = 0.25f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = "Done",
                            tint     = Color(0xFF4CAF50),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    else -> Row(
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // − button
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = if (completed > 0) 0.2f else 0.07f))
                                .clickable(enabled = completed > 0, onClick = onRemove),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Remove,
                                contentDescription = "Decrease",
                                tint     = Color.White.copy(alpha = if (completed > 0) 1f else 0.3f),
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        // + button
                        Box(
                            modifier = Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.2f))
                                .clickable(onClick = onAdd),
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
                            .background(
                                if (isDone) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.7f)
                            )
                    )
                }
            }
        }
    }
}
