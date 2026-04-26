package com.TBN.steade.ui.screens

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.TBN.steade.data.network.ApiAchievement
import com.TBN.steade.data.network.RetrofitClient
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.viewmodel.SteadEViewModel

private val categoryOrder = listOf(
    "Streaks", "Milestones", "Nutrition", "Fitness", "Mindfulness", "Study", "Work"
)

private val fallbackAchievements = listOf(
    ApiAchievement(1,  "First step",            "Complete your first day",                       "first_step.png",         "Streaks",     false, null, 0f,  1),
    ApiAchievement(2,  "Getting Warmed Up",      "Keep your habit alive for 3 days straight.",   "getting_warmed_up.png",  "Streaks",     false, null, 0f,  3),
    ApiAchievement(3,  "Locked In",              "Reach a 7-day streak",                         "locked_in.png",          "Streaks",     false, null, 0f,  7),
    ApiAchievement(4,  "Habit Formed",           "Complete a habit 10 times total",              "habit_formed.png",       "Milestones",  false, null, 0f,  10),
    ApiAchievement(5,  "On a Roll",              "Complete 50 habits total",                     "on_a_roll.png",          "Milestones",  false, null, 0f,  50),
    ApiAchievement(6,  "Century Club",           "Complete 100 habits total",                    "century_club.png",       "Milestones",  false, null, 0f,  100),
    ApiAchievement(7,  "First Bite",             "Log your first nutrition habit",               "first_bite.png",         "Nutrition",   false, null, 0f,  1),
    ApiAchievement(8,  "Clean Plate",            "Track all meals for 5 full days",              "clean_plate.png",        "Nutrition",   false, null, 0f,  5),
    ApiAchievement(9,  "Fuel Up",                "Complete 7 nutrition habits",                  "fuel_up.png",            "Nutrition",   false, null, 0f,  7),
    ApiAchievement(10, "First Sweat",            "Complete your first workout habit",            "first_sweat.png",        "Fitness",     false, null, 0f,  1),
    ApiAchievement(11, "Weekly Warrior",         "Work out 3 times in one week",                 "weekly_warrior.png",     "Fitness",     false, null, 0f,  3),
    ApiAchievement(12, "Iron Will",              "Complete 30 fitness habits",                   "iron_will.png",          "Fitness",     false, null, 0f,  30),
    ApiAchievement(13, "Calm Start",             "Complete your first mindfulness habit",        "calm_start.png",         "Mindfulness", false, null, 0f,  1),
    ApiAchievement(14, "Mind & Body",            "Complete 3 mindfulness habits",                "mind_and_body.png",      "Mindfulness", false, null, 0f,  3),
    ApiAchievement(15, "Well-Being",             "Complete 7 mindfulness habits",                "well_being.png",         "Mindfulness", false, null, 0f,  7),
    ApiAchievement(16, "First Focus",            "Log your first study session",                 "first_focus.png",        "Study",       false, null, 0f,  1),
    ApiAchievement(17, "Study Streak",           "Study 5 days in a row",                        "study_streak.png",       "Study",       false, null, 0f,  5),
    ApiAchievement(18, "Consistency Builder",    "Study 10 total hours",                         "consistency_builder.png","Study",       false, null, 0f,  10),
    ApiAchievement(19, "First Task",             "Complete your first work task",                "first_task.png",         "Work",        false, null, 0f,  1),
    ApiAchievement(20, "Getting Productive",     "Complete 5 tasks",                             "getting_productive.png", "Work",        false, null, 0f,  5),
    ApiAchievement(21, "Work Warrior",           "Complete 10 tasks",                            "work_warrior.png",       "Work",        false, null, 0f,  10),
)

@Composable
fun AchievementsScreen(navController: NavController, viewModel: SteadEViewModel) {
    LaunchedEffect(Unit) { viewModel.loadAchievements() }

    val displayList = viewModel.achievements.ifEmpty { fallbackAchievements }
    val unlocked    = displayList.count { it.isUnlocked }

    val grouped = displayList.groupBy { it.achievementType ?: "General" }
    val orderedKeys = categoryOrder.filter { it in grouped } +
            grouped.keys.filter { it !in categoryOrder }

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(Modifier.height(32.dp))
            Text("Achievements", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text("$unlocked / ${displayList.size} unlocked",
                color = Color.White.copy(alpha = 0.65f), fontSize = 14.sp)
            Spacer(Modifier.height(12.dp))
            val ratio = if (displayList.isEmpty()) 0f else unlocked.toFloat() / displayList.size
            LinearProgressIndicator(
                progress   = { ratio },
                modifier   = Modifier.fillMaxWidth().height(6.dp),
                color      = Color.White,
                trackColor = Color.White.copy(alpha = 0.2f),
                strokeCap  = androidx.compose.ui.graphics.StrokeCap.Round
            )
            Spacer(Modifier.height(20.dp))

            if (viewModel.achievementsLoading) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    orderedKeys.forEach { category ->
                        val items = grouped[category] ?: return@forEach
                        item {
                            Text(
                                category,
                                color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                            )
                        }
                        items(items) { achievement ->
                            AchievementCard(achievement)
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
fun AchievementCard(achievement: ApiAchievement) {
    val unlocked = achievement.isUnlocked
    val progress = if (achievement.thresholdValue > 0)
        (achievement.progress / achievement.thresholdValue).coerceIn(0f, 1f) else 0f

    Card(
        modifier = Modifier.fillMaxWidth().alpha(if (unlocked) 1f else 0.75f),
        shape    = RoundedCornerShape(14.dp),
        colors   = CardDefaults.cardColors(
            containerColor = if (unlocked) Color.White.copy(alpha = 0.22f) else Color.White.copy(alpha = 0.08f)
        )
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            // Icon / image
            Box(modifier = Modifier.size(56.dp), contentAlignment = Alignment.Center) {
                if (achievement.icon.endsWith(".png") || achievement.icon.endsWith(".jpg") || achievement.icon.endsWith(".webp")) {
                    AsyncImage(
                        model              = "${RetrofitClient.BASE_URL}images/achievements/${achievement.icon}",
                        contentDescription = achievement.title,
                        contentScale       = ContentScale.Fit,
                        modifier           = Modifier.size(56.dp)
                    )
                } else {
                    Text(achievement.icon, fontSize = 36.sp)
                }
            }

            Spacer(Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(achievement.title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                Text(achievement.description, color = Color.White.copy(alpha = 0.65f), fontSize = 12.sp, lineHeight = 16.sp)
                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress   = { progress },
                    modifier   = Modifier.fillMaxWidth().height(4.dp),
                    color      = if (unlocked) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.6f),
                    trackColor = Color.White.copy(alpha = 0.15f),
                    strokeCap  = androidx.compose.ui.graphics.StrokeCap.Round
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    if (unlocked) "✓ Unlocked"
                    else "${achievement.progress.toInt()} / ${achievement.thresholdValue}",
                    color    = if (unlocked) Color(0xFF4CAF50) else Color.White.copy(alpha = 0.5f),
                    fontSize = 11.sp,
                    fontWeight = if (unlocked) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}
