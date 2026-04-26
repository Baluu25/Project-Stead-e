package com.TBN.steade.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.TBN.steade.data.network.ApiAchievement
import com.TBN.steade.data.network.RetrofitClient
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.viewmodel.SteadEViewModel

private val categoryOrder = listOf("Streaks", "Milestones", "Nutrition", "Fitness", "Mindfulness", "Study", "Work")

private val fallbackAchievements = listOf(
    ApiAchievement(1, "First Step",        "Complete your first habit",              "flag",    "Streaks",    false, null, 0f, 1),
    ApiAchievement(2, "Getting Warmed Up", "Complete habits for 3 days in a row",   "fire",    "Streaks",    false, null, 0f, 3),
    ApiAchievement(3, "Locked In",         "Complete habits for 7 days in a row",   "bolt",    "Streaks",    false, null, 0f, 7),
    ApiAchievement(4, "Habit Formed",      "Complete a habit 10 times total",        "medal",   "Milestones", false, null, 0f, 10),
    ApiAchievement(5, "On a Roll",         "Complete 50 habits total",               "trending","Milestones", false, null, 0f, 50),
    ApiAchievement(6, "Century Club",      "Complete 100 habits total",              "trophy",  "Milestones", false, null, 0f, 100),
)

fun achievementIconToMaterial(icon: String): ImageVector = when {
    icon.contains("fire")    || icon.contains("flame")   -> Icons.Default.LocalFireDepartment
    icon.contains("bolt")    || icon.contains("light")   -> Icons.Default.Bolt
    icon.contains("flag")    || icon.contains("start")   -> Icons.Default.Flag
    icon.contains("medal")                               -> Icons.Default.WorkspacePremium
    icon.contains("trending")|| icon.contains("roll")    -> Icons.Default.TrendingUp
    icon.contains("trophy")  || icon.contains("cup")     -> Icons.Default.EmojiEvents
    icon.contains("star")                                -> Icons.Default.Star
    icon.contains("heart")                               -> Icons.Default.Favorite
    icon.contains("run")     || icon.contains("fitness") -> Icons.Default.FitnessCenter
    icon.contains("book")    || icon.contains("study")   -> Icons.Default.MenuBook
    icon.contains("brain")   || icon.contains("mind")    -> Icons.Default.Psychology
    icon.contains("check")                               -> Icons.Default.CheckCircle
    else                                                 -> Icons.Default.EmojiEvents
}

@Composable
fun AchievementsScreen(navController: NavController, viewModel: SteadEViewModel) {
    LaunchedEffect(Unit) { viewModel.loadAchievements() }

    val achievements  = viewModel.achievements.toList()
    val displayList   = if (achievements.isNotEmpty()) achievements else fallbackAchievements
    val unlockedCount = achievements.count { it.isUnlocked }

    val grouped     = displayList.groupBy { it.achievementType ?: "General" }
    val orderedKeys = categoryOrder.filter { it in grouped } + grouped.keys.filter { it !in categoryOrder }

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(Modifier.height(32.dp))
            Text("Achievements", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(
                if (achievements.isNotEmpty()) "$unlockedCount / ${achievements.size} unlocked"
                else "Complete habits to unlock achievements!",
                color = Color.White.copy(alpha = 0.65f), fontSize = 14.sp
            )
            Spacer(Modifier.height(12.dp))

            if (achievements.isNotEmpty()) {
                val ratio = unlockedCount.toFloat() / achievements.size.coerceAtLeast(1)
                LinearProgressIndicator(
                    progress    = { ratio },
                    modifier    = Modifier.fillMaxWidth().height(6.dp),
                    color       = Color.White, trackColor = Color.White.copy(alpha = 0.2f),
                    strokeCap   = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Spacer(Modifier.height(20.dp))
            }

            if (viewModel.achievementsLoading) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    orderedKeys.forEach { category ->
                        val categoryItems = grouped[category] ?: return@forEach
                        item {
                            Text(category, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
                        }
                        items(categoryItems) { achievement ->
                            AchievementCard(achievement, achievements.isNotEmpty())
                        }
                    }
                    item { Spacer(Modifier.height(8.dp)) }
                }
            }
            BottomNavBar(navController)
        }
    }
}

@Composable
fun AchievementCard(achievement: ApiAchievement, isFromApi: Boolean) {
    val unlocked = achievement.isUnlocked
    val progress = if (achievement.thresholdValue > 0)
        (achievement.progress / achievement.thresholdValue).coerceIn(0f, 1f)
    else if (unlocked) 1f else 0f

    Card(
        modifier = Modifier.fillMaxWidth().alpha(if (unlocked) 1f else 0.75f),
        shape    = RoundedCornerShape(14.dp),
        border   = BorderStroke(1.dp, Color.White.copy(alpha = if (unlocked) 0.5f else 0.2f)),
        colors   = CardDefaults.cardColors(
            containerColor = if (unlocked) Color.White.copy(alpha = 0.22f) else Color.White.copy(alpha = 0.08f)
        )
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {

            // Icon circle
            Box(
                modifier          = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(if (unlocked) Color(0xFFFFD700).copy(alpha = 0.18f) else Color.White.copy(alpha = 0.1f)),
                contentAlignment  = Alignment.Center
            ) {
                val iconStr = achievement.icon
                if (iconStr.endsWith(".png") || iconStr.endsWith(".jpg") || iconStr.endsWith(".webp")) {
                    AsyncImage(
                        model            = "${RetrofitClient.BASE_URL}images/achievements/$iconStr",
                        contentDescription = achievement.title,
                        contentScale     = ContentScale.Fit,
                        modifier         = Modifier.size(36.dp)
                    )
                } else {
                    Icon(
                        achievementIconToMaterial(iconStr),
                        contentDescription = achievement.title,
                        tint     = if (unlocked) Color(0xFFFFD700) else Color.White.copy(alpha = 0.45f),
                        modifier = Modifier.size(30.dp)
                    )
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(achievement.title, color = Color.White, fontSize = 15.sp,
                        fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    if (unlocked) {
                        Icon(Icons.Default.EmojiEvents, contentDescription = null,
                            tint = Color(0xFFFFD700), modifier = Modifier.size(18.dp))
                    }
                }
                Text(achievement.description, color = Color.White.copy(alpha = 0.65f),
                    fontSize = 12.sp, lineHeight = 16.sp)
                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress  = { progress },
                    modifier  = Modifier.fillMaxWidth().height(4.dp),
                    color     = if (unlocked) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.6f),
                    trackColor = Color.White.copy(alpha = 0.15f),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Spacer(Modifier.height(4.dp))
                if (unlocked) {
                    val dateStr = achievement.unlockedAt?.take(10) ?: ""
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CheckCircle, contentDescription = null,
                            tint = Color(0xFF4CAF50), modifier = Modifier.size(12.dp))
                        Spacer(Modifier.width(3.dp))
                        Text(
                            if (dateStr.isNotEmpty()) "Unlocked $dateStr" else "Unlocked",
                            color = Color(0xFF4CAF50), fontSize = 11.sp, fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    Text("${achievement.progress.toInt()} / ${achievement.thresholdValue}",
                        color = Color.White.copy(alpha = 0.5f), fontSize = 11.sp)
                }
            }
        }
    }
}
