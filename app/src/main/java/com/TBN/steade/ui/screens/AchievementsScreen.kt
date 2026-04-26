package com.TBN.steade.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
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

// Fallback achievements - used when API returns empty (user has no achievements yet)
private val fallbackAchievements = listOf(
    ApiAchievement(1,  "First Step",            "Complete your first habit",                         "đźŚź",  "Streaks",     false, null, 0f,  1),
    ApiAchievement(2,  "Getting Warmed Up",     "Complete habits for 3 days in a row",               "đź”Ą",  "Streaks",     false, null, 0f,  3),
    ApiAchievement(3,  "Locked In",             "Complete habits for 7 days in a row",               "đź’Ş",  "Streaks",     false, null, 0f,  7),
    ApiAchievement(4,  "Habit Formed",          "Complete a habit 10 times total",                   "đźŹ…",  "Milestones",  false, null, 0f,  10),
    ApiAchievement(5,  "On a Roll",             "Complete 50 habits total",                          "đźŚŠ",  "Milestones",  false, null, 0f,  50),
    ApiAchievement(6,  "Century Club",          "Complete 100 habits total",                         "đź’Ż",  "Milestones",  false, null, 0f,  100),
)

@Composable
fun AchievementsScreen(navController: NavController, viewModel: SteadEViewModel) {
    // Always refresh when screen opens
    LaunchedEffect(Unit) { viewModel.loadAchievements() }

    // Use API achievements, fallback only if API returns empty list
    val achievements = viewModel.achievements.toList()
    val displayList = if (achievements.isNotEmpty()) achievements else fallbackAchievements
    val unlockedCount = achievements.count { it.isUnlocked }
    
    val grouped = displayList.groupBy { it.achievementType ?: "General" }
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

            // Progress bar only when we have real data
            if (achievements.isNotEmpty()) {
                val ratio = unlockedCount.toFloat() / achievements.size.coerceAtLeast(1)
                LinearProgressIndicator(
                    progress = { ratio }, 
                    modifier = Modifier.fillMaxWidth().height(6.dp),
                    color = Color.White, trackColor = Color.White.copy(alpha = 0.2f), 
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Spacer(Modifier.height(20.dp))
            }

            // Show achievements list
            if (viewModel.achievementsLoading) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    orderedKeys.forEach { category ->
                        val items = grouped[category] ?: return@forEach
                        item { 
                            Text(category, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold, 
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)) 
                        }
                        items(items) { achievement -> AchievementCard(achievement, achievements.isNotEmpty()) }
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
        (achievement.progress.toFloat() / achievement.thresholdValue).coerceIn(0f, 1f) 
        else if (unlocked) 1f else 0f

    Card(modifier = Modifier.fillMaxWidth().alpha(if (unlocked) 1f else 0.75f),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = if (unlocked) 0.5f else 0.2f)),
        colors = CardDefaults.cardColors(
            containerColor = if (unlocked) Color.White.copy(alpha = 0.22f) else Color.White.copy(alpha = 0.08f)
        )) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Icon
            Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                val iconStr = achievement.icon
                if (iconStr.endsWith(".png") || iconStr.endsWith(".jpg") || iconStr.endsWith(".webp")) {
                    AsyncImage(
                        model = "${RetrofitClient.BASE_URL}images/achievements/$iconStr", 
                        contentDescription = achievement.title,
                        contentScale = ContentScale.Fit, modifier = Modifier.size(56.dp)
                    )
                } else { 
                    // Use emoji/text as icon
                    Text(iconStr, fontSize = 32.sp)
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(achievement.title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold, 
                        modifier = Modifier.weight(1f))
                    if (unlocked) { Text("đźŹ†", fontSize = 16.sp) }
                }
                Text(achievement.description, color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp, lineHeight = 16.sp)
                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { progress }, 
                    modifier = Modifier.fillMaxWidth().height(4.dp),
                    color = if (unlocked) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.6f), 
                    trackColor = Color.White.copy(alpha = 0.15f),
                    strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = if (unlocked) { 
                        val dateStr = achievement.unlockedAt?.take(10) ?: ""; 
                        if (dateStr.isNotEmpty()) "âś“ Unlocked $dateStr" else "âś“ Unlocked" 
                    } else { "${achievement.progress} / ${achievement.thresholdValue}" },
                    color = if (unlocked) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.5f),
                    fontSize = 11.sp, fontWeight = if (unlocked) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}



