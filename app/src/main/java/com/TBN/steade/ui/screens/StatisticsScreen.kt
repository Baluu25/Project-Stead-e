package com.TBN.steade.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.viewmodel.SteadEViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
@Composable
fun StatisticsScreen(navController: NavController, viewModel: SteadEViewModel) {
    LaunchedEffect(Unit) { viewModel.loadStatistics() }

    val stats   = viewModel.statistics
    val loading = viewModel.statisticsLoading
    val error   = viewModel.statisticsError

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(32.dp))
                Text("Statistics", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(20.dp))

                when {
                    loading -> {
                        Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = Color.White)
                        }
                    }
                    error != null -> {
                        Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(error, color = Color.White.copy(alpha = 0.75f), fontSize = 14.sp)
                                Spacer(Modifier.height(12.dp))
                                Button(onClick = { viewModel.loadStatistics() },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.2f))) {
                                    Text("Retry", color = Color.White)
                                }
                            }
                        }
                    }
                    else -> {
                        // Summary cards
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            StatCard("Total Habits", "${stats?.totalHabits ?: 0}",        Icons.Default.Tag,                 Modifier.weight(1f))
                            StatCard("Active",       "${stats?.activeHabits ?: 0}",        Icons.Default.CheckCircle,         Modifier.weight(1f))
                            StatCard("Streak",       "${stats?.currentStreak ?: 0} days",  Icons.Default.LocalFireDepartment, Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            StatCard("Best Streak",  "${stats?.longestStreak ?: 0} days",  Icons.Default.EmojiEvents,        Modifier.weight(1f))
                            StatCard("This Week",    "${stats?.completionsThisWeek ?: 0}", Icons.Default.CalendarToday,      Modifier.weight(1f))
                        }

                        Spacer(Modifier.height(24.dp))
                        Text("Activity (Last 7 Days)", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                        Spacer(Modifier.height(12.dp))

                        // Build 7-point list aligned to Mon–Sun of the current week
                        val fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                        val today = LocalDate.now()
                        val last7 = (6 downTo 0).map { today.minusDays(it.toLong()) }
                        val dailyMap = stats?.dailyCompletions ?: emptyMap()
                        val weekCounts = last7.map { dailyMap[it.format(fmt)] ?: 0 }
                        val dateLabels = last7.map { it.dayOfMonth.toString() }
                        WeekLineChart(weekCounts, dateLabels)

                        // Category breakdown
                        val catMap = stats?.categoryBreakdown ?: emptyMap()
                        if (catMap.isNotEmpty()) {
                            Spacer(Modifier.height(24.dp))
                            Text("Category Breakdown", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                            Spacer(Modifier.height(12.dp))
                            val maxCat = catMap.values.maxOrNull()?.toFloat()?.coerceAtLeast(1f) ?: 1f
                            catMap.entries.sortedByDescending { it.value }.forEach { (category, count) ->
                                CategoryProgressBar(category, count / maxCat, count)
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
            BottomNavBar(navController)
        }
    }
}

@Composable
fun StatCard(label: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(label, color = Color.White.copy(alpha = 0.65f), fontSize = 10.sp)
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.size(16.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                value,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun WeekLineChart(rawCounts: List<Int>, dateLabels: List<String>) {
    val maxCount   = rawCounts.maxOrNull()?.coerceAtLeast(1) ?: 1
    val midCount   = maxCount / 2
    val normalized = rawCounts.map { it.toFloat() / maxCount }

    Card(modifier = Modifier.fillMaxWidth().height(180.dp), shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))) {

        Row(modifier = Modifier.fillMaxSize().padding(top = 12.dp, bottom = 8.dp, start = 8.dp, end = 12.dp)) {

            // Y-axis labels (max at top → 0 at bottom), padded to match canvas height
            Column(
                modifier = Modifier.width(26.dp).fillMaxHeight().padding(bottom = 18.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(maxCount, midCount, 0).forEach { v ->
                    Text("$v", color = Color.White.copy(alpha = 0.55f),
                        fontSize = 9.sp, textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth())
                }
            }

            Spacer(Modifier.width(4.dp))

            // Chart + X-axis date labels
            Column(modifier = Modifier.weight(1f).fillMaxHeight()) {
                Canvas(modifier = Modifier.weight(1f).fillMaxWidth()) {
                    val pts = normalized.take(7)
                    if (pts.size < 2) return@Canvas
                    val stepX = size.width / (pts.size - 1).coerceAtLeast(1)

                    // Horizontal gridlines at 0 %, 50 %, 100 %
                    listOf(0f, 0.5f, 1f).forEach { frac ->
                        val y = size.height * (1f - frac)
                        drawLine(Color.White.copy(alpha = 0.1f), Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                    }

                    // Line
                    val path = Path()
                    pts.forEachIndexed { i, v ->
                        val x = i * stepX
                        val y = size.height * (1f - v.coerceIn(0f, 1f))
                        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                    }
                    drawPath(path, Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))

                    // Dots
                    pts.forEachIndexed { i, v ->
                        drawCircle(Color.White, 5f, Offset(i * stepX, size.height * (1f - v.coerceIn(0f, 1f))))
                    }
                }

                // X-axis: date numbers
                Row(modifier = Modifier.fillMaxWidth().height(18.dp),
                    horizontalArrangement = Arrangement.SpaceBetween) {
                    dateLabels.forEach { d ->
                        Text(d, color = Color.White.copy(alpha = 0.55f),
                            fontSize = 9.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryProgressBar(name: String, progress: Float, count: Int) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(name, color = Color.White, fontSize = 14.sp)
            Text("$count completions", color = Color.White.copy(alpha = 0.65f), fontSize = 13.sp)
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(progress = { progress.coerceIn(0f, 1f) },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = Color.White, trackColor = Color.White.copy(alpha = 0.2f),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round)
    }
}
