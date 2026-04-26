package com.TBN.steade.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
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
                            StatCard("Total Habits",  "${stats?.totalHabits ?: 0}",         Modifier.weight(1f))
                            StatCard("Active",        "${stats?.activeHabits ?: 0}",         Modifier.weight(1f))
                            StatCard("Streak",        "${stats?.currentStreak ?: 0}d",       Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(10.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            StatCard("Best Streak",   "${stats?.longestStreak ?: 0}d",       Modifier.weight(1f))
                            StatCard("This Week",     "${stats?.completionsThisWeek ?: 0}",  Modifier.weight(1f))
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
                        val maxCount = weekCounts.maxOrNull()?.toFloat()?.coerceAtLeast(1f) ?: 1f
                        val weekPoints = weekCounts.map { it / maxCount }
                        WeekLineChart(weekPoints)

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
fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))) {
        Column(modifier = Modifier.padding(14.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(value, color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(label, color = Color.White.copy(alpha = 0.65f), fontSize = 10.sp)
        }
    }
}

@Composable
fun WeekLineChart(dataPoints: List<Float>) {
    val dayLabels = listOf("M","T","W","T","F","S","S")
    Card(modifier = Modifier.fillMaxWidth().height(180.dp), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))) {
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize().padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 32.dp)) {
                val pts = dataPoints.take(7)
                if (pts.size < 2) return@Canvas
                val stepX = size.width / (pts.size - 1).coerceAtLeast(1)
                val path  = Path()
                pts.forEachIndexed { i, v ->
                    val x = i * stepX
                    val y = size.height * (1f - v.coerceIn(0f, 1f))
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(path, Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))
                pts.forEachIndexed { i, v ->
                    drawCircle(Color.White, 5f, Offset(i * stepX, size.height * (1f - v.coerceIn(0f, 1f))))
                }
            }
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                dayLabels.forEach { Text(it, color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp) }
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
