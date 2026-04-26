package com.TBN.steade.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

// Fallback achievements shown when no API data is available
private val fallbackAchievements = listOf(
    ApiAchievement(1,  "First Step",       "Complete your first habit",         "🌱",  true),
    ApiAchievement(2,  "3 Day Streak",      "Keep it up for 3 days",             "🔥",  true),
    ApiAchievement(3,  "Week Master",       "7 day streak reached",              "🏆",  false),
    ApiAchievement(4,  "Hydration Hero",    "Drink 10L of water in a week",      "💧",  true),
    ApiAchievement(5,  "Iron Lung",         "3 days without smoking",            "🫁",  false),
    ApiAchievement(6,  "Early Bird",        "Complete a habit before 8 AM",      "🌅",  false),
    ApiAchievement(7,  "Goal Setter",       "Create your first goal",            "🎯",  true),
    ApiAchievement(8,  "30 Day Champion",   "30 consecutive days",               "🥇",  false)
)

@Composable
fun AchievementsScreen(navController: NavController, viewModel: SteadEViewModel) {
    LaunchedEffect(Unit) { viewModel.loadAchievements() }

    val displayList = viewModel.achievements.ifEmpty { fallbackAchievements }
    val unlocked    = displayList.count { it.isUnlocked }

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(Modifier.height(32.dp))
            Text("Achievements", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(
                "$unlocked / ${displayList.size} unlocked",
                color    = Color.White.copy(alpha = 0.65f),
                fontSize = 14.sp
            )

            // Overall progress bar
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
                LazyVerticalGrid(
                    columns               = GridCells.Fixed(2),
                    modifier              = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement   = Arrangement.spacedBy(12.dp)
                ) {
                    items(displayList) { achievement ->
                        AchievementCard(achievement)
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            BottomNavBar(navController)
        }
    }
}

@Composable
fun AchievementCard(achievement: ApiAchievement) {
    val unlocked = achievement.isUnlocked
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape    = RoundedCornerShape(16.dp),
        colors   = CardDefaults.cardColors(
            containerColor = if (unlocked) Color.White.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.06f)
        )
    ) {
        Column(
            modifier            = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (achievement.icon.endsWith(".png") || achievement.icon.endsWith(".jpg") || achievement.icon.endsWith(".webp")) {
                AsyncImage(
                    model             = "${RetrofitClient.BASE_URL}images/achievements/${achievement.icon}",
                    contentDescription = achievement.title,
                    contentScale      = ContentScale.Fit,
                    modifier          = Modifier.size(48.dp).alpha(if (unlocked) 1f else 0.25f)
                )
            } else {
                Text(
                    achievement.icon,
                    fontSize = 40.sp,
                    modifier = Modifier.alpha(if (unlocked) 1f else 0.25f)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                achievement.title,
                color      = Color.White,
                fontSize   = 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign  = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                achievement.description,
                color     = Color.White.copy(alpha = 0.6f),
                fontSize  = 11.sp,
                textAlign = TextAlign.Center,
                lineHeight= 15.sp
            )
            if (unlocked) {
                Spacer(Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Green.copy(alpha = 0.25f)
                ) {
                    Text(
                        "✓ Unlocked",
                        color    = Color.Green,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            } else {
                Spacer(Modifier.height(8.dp))
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White.copy(alpha = 0.08f)
                ) {
                    Text(
                        "🔒 Locked",
                        color    = Color.White.copy(alpha = 0.4f),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}
