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

@Composable
fun StatisticsScreen(navController: NavController, viewModel: SteadEViewModel) {
    LaunchedEffect(Unit) { viewModel.loadStatistics() }
    val stats = viewModel.statistics

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState())) {
                Spacer(Modifier.height(32.dp))
                Text("Statistics", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(20.dp))

                // Summary cards
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard("Total Habits", "${stats?.totalHabits ?: 0}", Modifier.weight(1f))
                    StatCard("Active",       "${stats?.activeHabits ?: 0}", Modifier.weight(1f))
                    StatCard("Streak",       "${stats?.currentStreak ?: 0}d", Modifier.weight(1f))
                }
                Spacer(Modifier.height(10.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCard("Best Streak", "${stats?.longestStreak ?: 0}d", Modifier.weight(1f))
                    StatCard("This Week",   "${stats?.completionsThisWeek ?: 0}", Modifier.weight(1f))
                }

                Spacer(Modifier.height(24.dp))
                Text("Activity (Last 7 Days)", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(12.dp))

                // Build week chart from daily_completions
                val rawDaily = stats?.dailyCompletions ?: emptyList()
                val maxCount = rawDaily.maxOfOrNull { it.count }?.toFloat()?.coerceAtLeast(1f) ?: 1f
                val weekPoints = if (rawDaily.size >= 7)
                    rawDaily.takeLast(7).map { it.count / maxCount }
                else
                    List(7) { 0f }
                WeekLineChart(weekPoints)

                // Category breakdown
                if (!stats?.categoryBreakdown.isNullOrEmpty()) {
                    Spacer(Modifier.height(24.dp))
                    Text("Category Breakdown", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(12.dp))
                    val maxCat = stats!!.categoryBreakdown.maxOfOrNull { it.count }?.toFloat()?.coerceAtLeast(1f) ?: 1f
                    stats.categoryBreakdown.forEach { cb ->
                        CategoryProgressBar(cb.category ?: "Other", cb.count / maxCat)
                        Spacer(Modifier.height(8.dp))
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
    Card(modifier = Modifier.fillMaxWidth().height(180.dp), shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))) {
        Box(modifier = Modifier.fillMaxSize()) {
            Canvas(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                val pts = dataPoints.take(7)
                if (pts.size < 2) return@Canvas
                val stepX = size.width / (pts.size - 1).coerceAtLeast(1)
                val path  = Path()
                pts.forEachIndexed { i, v ->
                    val x = i * stepX; val y = size.height * (1f - v.coerceIn(0f, 1f))
                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }
                drawPath(path, Color.White, style = Stroke(width = 3f, cap = StrokeCap.Round))
                pts.forEachIndexed { i, v ->
                    drawCircle(Color.White, 5f, Offset(i * stepX, size.height * (1f - v.coerceIn(0f,1f))))
                }
            }
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("M","T","W","T","F","S","S").forEach { Text(it, color = Color.White.copy(alpha = 0.5f), fontSize = 10.sp) }
            }
        }
    }
}

@Composable
fun CategoryProgressBar(name: String, progress: Float) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(name, color = Color.White, fontSize = 14.sp)
            Text("${(progress * 100).toInt()}%", color = Color.White, fontSize = 14.sp)
        }
        Spacer(Modifier.height(4.dp))
        LinearProgressIndicator(progress = { progress.coerceIn(0f,1f) },
            modifier = Modifier.fillMaxWidth().height(8.dp),
            color = Color.White, trackColor = Color.White.copy(alpha = 0.2f),
            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round)
    }
}
