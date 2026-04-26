package com.TBN.steade.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.TBN.steade.R
import com.TBN.steade.ui.navigation.Screen
import com.TBN.steade.ui.theme.SteadeBlue
import com.TBN.steade.ui.theme.SteadeMidPurple
import com.TBN.steade.ui.theme.SteadeRed

// ─── Font Awesome class name → emoji mapping ─────────────────────────────────

fun habitIconToEmoji(icon: String?): String {
    if (icon.isNullOrBlank()) return "⭐"
    if (!icon.startsWith("fa-")) return icon  // already emoji or plain text
    return when {
        // Nutrition
        icon.contains("apple")           -> "🍎"
        icon.contains("carrot")          -> "🥕"
        icon.contains("lemon")           -> "🍋"
        icon.contains("bowl")            -> "🥣"
        icon.contains("mug-saucer")      -> "☕"
        icon.contains("burger")          -> "🍔"
        icon.contains("fish")            -> "🐟"
        icon.contains("egg")             -> "🥚"
        icon.contains("droplet")         -> "💧"
        icon.contains("wine")            -> "🍷"
        // Fitness
        icon.contains("dumbbell")        -> "🏋️"
        icon.contains("person-running")  -> "🏃"
        icon.contains("person-walking")  -> "🚶"
        icon.contains("bicycle")         -> "🚲"
        icon.contains("heart-pulse")     -> "💓"
        icon.contains("fire")            -> "🔥"
        icon.contains("stopwatch")       -> "⏱️"
        icon.contains("shoe")            -> "👟"
        icon.contains("weight-scale")    -> "⚖️"
        icon.contains("swimming")        -> "🏊"
        // Mindfulness
        icon.contains("brain")           -> "🧠"
        icon.contains("spa")             -> "🌸"
        icon.contains("face-smile")      -> "😊"
        icon.contains("feather")         -> "🪶"
        icon.contains("leaf")            -> "🌿"
        icon.contains("om")              -> "🕉️"
        icon.contains("cloud")           -> "☁️"
        icon.contains("wind")            -> "🌬️"
        icon.contains("moon")            -> "🌙"
        icon.contains("heart")           -> "❤️"
        // Study
        icon.contains("book-open")       -> "📖"
        icon.contains("book")            -> "📚"
        icon.contains("graduation")      -> "🎓"
        icon.contains("pencil")          -> "✏️"
        icon.contains("lightbulb")       -> "💡"
        icon.contains("microscope")      -> "🔬"
        icon.contains("flask")           -> "🧪"
        icon.contains("language")        -> "🌐"
        icon.contains("pen")             -> "🖊️"
        // Work
        icon.contains("briefcase")       -> "💼"
        icon.contains("laptop")          -> "💻"
        icon.contains("computer")        -> "🖥️"
        icon.contains("calendar")        -> "📅"
        icon.contains("chart-line")      -> "📈"
        icon.contains("chart")           -> "📊"
        icon.contains("envelope")        -> "📧"
        icon.contains("users")           -> "👥"
        icon.contains("mug-hot")         -> "☕"
        icon.contains("clock")           -> "🕐"
        // Generic
        icon.contains("star")            -> "⭐"
        else                             -> "⭐"
    }
}

// ─── Brand gradient matching CSS: linear-gradient(135deg, #ff2a00, #2a51ff) ──────────

val SteadeGradientBrush = Brush.linearGradient(
    colors = listOf(SteadeRed, SteadeMidPurple, SteadeBlue),
    start  = Offset(0f, 0f),
    end    = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)

// ─── Full-screen gradient background ─────────────────────────────────────────

@Composable
fun MainGradientBackground(
    showShadow: Boolean = false,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = SteadeGradientBrush)
    ) {
        if (showShadow) {
            Image(
                painter      = painterResource(id = R.drawable.shadow),
                contentDescription = null,
                modifier     = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha        = 0.35f
            )
        }
        content()
    }
}

// ─── White pill / frosted card background ────────────────────────────────────

val cardBg = Color.White.copy(alpha = 0.15f)

// ─── Bottom Navigation Bar ────────────────────────────────────────────────────

data class NavItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)

val navItems = listOf(
    NavItem(Screen.Dashboard,    Icons.Default.Home,       "Home"),
    NavItem(Screen.Habits,       Icons.AutoMirrored.Filled.List,       "Habits"),
    NavItem(Screen.Goals,        Icons.Default.Star,       "Goals"),
    NavItem(Screen.Statistics,   Icons.Default.BarChart,   "Stats"),
    NavItem(Screen.Achievements, Icons.Default.CheckCircle,"Awards"),
    NavItem(Screen.Settings,     Icons.Default.Settings,   "Settings")
)

@Composable
fun BottomNavBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(24.dp))
            .padding(horizontal = 4.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        navItems.forEach { item ->
            val selected = currentRoute == item.screen.route
            NavIcon(
                icon     = item.icon,
                label    = item.label,
                selected = selected,
                onClick  = {
                    if (!selected) {
                        navController.navigate(item.screen.route) {
                            popUpTo(Screen.Dashboard.route) { saveState = true }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun NavIcon(
    icon: ImageVector,
    label: String,
    selected: Boolean = false,
    onClick: () -> Unit = {}
) {
    val tint = if (selected) Color.White else Color.White.copy(alpha = 0.4f)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 4.dp)
    ) {
        IconButton(onClick = onClick, modifier = Modifier.size(40.dp)) {
            Icon(icon, contentDescription = label, tint = tint, modifier = Modifier.size(22.dp))
        }
        Text(
            text       = label,
            color      = tint,
            fontSize   = 9.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            maxLines   = 1
        )
    }
}

// ─── Frosted Card ─────────────────────────────────────────────────────────────

@Composable
fun FrostedCard(
    modifier: Modifier = Modifier,
    alpha: Float = 0.15f,
    cornerRadius: Int = 16,
    content: @Composable ColumnScope.() -> Unit
) {
    androidx.compose.foundation.layout.Column(
        modifier = modifier
            .background(
                Color.White.copy(alpha = alpha),
                RoundedCornerShape(cornerRadius.dp)
            )
            .padding(16.dp),
        content = content
    )
}
