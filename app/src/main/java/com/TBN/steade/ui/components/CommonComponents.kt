package com.TBN.steade.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Cake
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Computer
import androidx.compose.material.icons.filled.Cookie
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.DirectionsBike
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.DinnerDining
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Hiking
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Laptop
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.LocalCafe
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.LunchDining
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MonitorHeart
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Scale
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Science
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.material.icons.filled.SportsBasketball
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrackChanges
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.automirrored.filled.List
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

// ─── FA class / emoji → Material ImageVector ─────────────────────────────────

fun habitIconToMaterialIcon(icon: String?): ImageVector {
    val s = icon.orEmpty()
    return when {
        s.contains("dumbbell")           -> Icons.Default.FitnessCenter
        s.contains("person-running")     -> Icons.Default.DirectionsRun
        s.contains("person-walking")     -> Icons.Default.DirectionsWalk
        s.contains("person-biking")      -> Icons.Default.DirectionsBike
        s.contains("bicycle")            -> Icons.Default.DirectionsBike
        s.contains("person-hiking")      -> Icons.Default.Hiking
        s.contains("heart-pulse")        -> Icons.Default.MonitorHeart
        s.contains("heartbeat")          -> Icons.Default.MonitorHeart
        s.contains("fire")               -> Icons.Default.LocalFireDepartment
        s.contains("stopwatch")          -> Icons.Default.Timer
        s.contains("shoe")               -> Icons.Default.DirectionsWalk
        s.contains("weight-scale")       -> Icons.Default.Scale
        s.contains("swimming")           -> Icons.Default.Pool
        s.contains("futbol")             -> Icons.Default.SportsSoccer
        s.contains("basketball")         -> Icons.Default.SportsBasketball
        s.contains("dog") || s.contains("cat") || s.contains("dove") -> Icons.Default.Pets
        s.contains("apple")              -> Icons.Default.Restaurant
        s.contains("carrot")             -> Icons.Default.Eco
        s.contains("lemon")              -> Icons.Default.Restaurant
        s.contains("bowl")               -> Icons.Default.DinnerDining
        s.contains("plate-wheat")        -> Icons.Default.DinnerDining
        s.contains("mug-saucer")         -> Icons.Default.LocalCafe
        s.contains("burger")             -> Icons.Default.LunchDining
        s.contains("fish")               -> Icons.Default.Restaurant
        s.contains("egg")                -> Icons.Default.Restaurant
        s.contains("utensils")           -> Icons.Default.Restaurant
        s.contains("cookie")             -> Icons.Default.Cookie
        s.contains("cake")               -> Icons.Default.Cake
        s.contains("droplet")            -> Icons.Default.WaterDrop
        s.contains("wine")               -> Icons.Default.LocalBar
        s.contains("brain")              -> Icons.Default.Psychology
        s.contains("spa")                -> Icons.Default.Spa
        s.contains("face-smile")         -> Icons.Default.SentimentSatisfied
        s.contains("feather")            -> Icons.Default.Edit
        s.contains("leaf")               -> Icons.Default.Eco
        s.contains("om")                 -> Icons.Default.SelfImprovement
        s.contains("cloud")              -> Icons.Default.Cloud
        s.contains("wind")               -> Icons.Default.Air
        s.contains("moon")               -> Icons.Default.Bedtime
        s.contains("sun")                -> Icons.Default.WbSunny
        s.contains("tree")               -> Icons.Default.Park
        s.contains("heart")              -> Icons.Default.Favorite
        s.contains("book-open")          -> Icons.Default.MenuBook
        s.contains("book")               -> Icons.Default.Book
        s.contains("graduation")         -> Icons.Default.School
        s.contains("pencil")             -> Icons.Default.Edit
        s.contains("lightbulb")          -> Icons.Default.Lightbulb
        s.contains("microscope")         -> Icons.Default.Biotech
        s.contains("flask")              -> Icons.Default.Science
        s.contains("language")           -> Icons.Default.Translate
        s.contains("calculator")         -> Icons.Default.Calculate
        s.contains("glasses")            -> Icons.Default.Visibility
        s.contains("ruler")              -> Icons.Default.Straighten
        s.contains("chalkboard") || s.contains("school") -> Icons.Default.School
        s.contains("pen")                -> Icons.Default.Create
        s.contains("briefcase")          -> Icons.Default.Work
        s.contains("laptop")             -> Icons.Default.Laptop
        s.contains("computer")           -> Icons.Default.Computer
        s.contains("building")           -> Icons.Default.Business
        s.contains("phone")              -> Icons.Default.Phone
        s.contains("file-lines")         -> Icons.Default.Description
        s.contains("folder")             -> Icons.Default.Folder
        s.contains("print")              -> Icons.Default.Print
        s.contains("calendar")           -> Icons.Default.EventAvailable
        s.contains("chart-line")         -> Icons.Default.TrendingUp
        s.contains("chart")              -> Icons.Default.BarChart
        s.contains("envelope")           -> Icons.Default.Email
        s.contains("users")              -> Icons.Default.Group
        s.contains("mug-hot")            -> Icons.Default.LocalCafe
        s.contains("clock")              -> Icons.Default.AccessTime
        // Emoji from older Android picker entries
        s.contains("🏋") || s.contains("💪") -> Icons.Default.FitnessCenter
        s.contains("🧘")                 -> Icons.Default.SelfImprovement
        s.contains("🍎") || s.contains("🥕") || s.contains("🍋") -> Icons.Default.Restaurant
        s.contains("📖") || s.contains("📚") -> Icons.Default.MenuBook
        s.contains("💻")                 -> Icons.Default.Laptop
        s.contains("💧")                 -> Icons.Default.WaterDrop
        s.contains("🏃")                 -> Icons.Default.DirectionsRun
        s.contains("🌱")                 -> Icons.Default.Eco
        s.contains("🎯")                 -> Icons.Default.TrackChanges
        s.contains("🛌")                 -> Icons.Default.Bedtime
        else                             -> Icons.Default.Star
    }
}

// ─── Category-specific icon lists (FA class → Material Icon) ─────────────────

val habitCategoryIcons: Map<String, List<Pair<String, ImageVector>>> = mapOf(
    "Nutrition" to listOf(
        "fa-solid fa-apple-whole"  to Icons.Default.Restaurant,
        "fa-solid fa-carrot"       to Icons.Default.Eco,
        "fa-solid fa-lemon"        to Icons.Default.Restaurant,
        "fa-solid fa-bowl-food"    to Icons.Default.DinnerDining,
        "fa-solid fa-mug-saucer"   to Icons.Default.LocalCafe,
        "fa-solid fa-burger"       to Icons.Default.LunchDining,
        "fa-solid fa-fish"         to Icons.Default.Restaurant,
        "fa-solid fa-egg"          to Icons.Default.Restaurant,
        "fa-solid fa-droplet"      to Icons.Default.WaterDrop,
        "fa-solid fa-wine-bottle"  to Icons.Default.LocalBar,
        "fa-solid fa-utensils"     to Icons.Default.Restaurant,
        "fa-solid fa-plate-wheat"  to Icons.Default.DinnerDining,
        "fa-solid fa-cookie"       to Icons.Default.Cookie,
        "fa-solid fa-cake-candles" to Icons.Default.Cake,
        "fa-solid fa-mug-hot"      to Icons.Default.LocalCafe,
    ),
    "Fitness" to listOf(
        "fa-solid fa-dumbbell"         to Icons.Default.FitnessCenter,
        "fa-solid fa-person-running"   to Icons.Default.DirectionsRun,
        "fa-solid fa-person-walking"   to Icons.Default.DirectionsWalk,
        "fa-solid fa-bicycle"          to Icons.Default.DirectionsBike,
        "fa-solid fa-heart-pulse"      to Icons.Default.MonitorHeart,
        "fa-solid fa-fire"             to Icons.Default.LocalFireDepartment,
        "fa-solid fa-stopwatch"        to Icons.Default.Timer,
        "fa-solid fa-shoe-prints"      to Icons.Default.DirectionsWalk,
        "fa-solid fa-weight-scale"     to Icons.Default.Scale,
        "fa-solid fa-person-swimming"  to Icons.Default.Pool,
        "fa-solid fa-person-biking"    to Icons.Default.DirectionsBike,
        "fa-solid fa-person-hiking"    to Icons.Default.Hiking,
        "fa-solid fa-futbol"           to Icons.Default.SportsSoccer,
        "fa-solid fa-basketball"       to Icons.Default.SportsBasketball,
        "fa-solid fa-dog"              to Icons.Default.Pets,
        "fa-solid fa-heartbeat"        to Icons.Default.MonitorHeart,
    ),
    "Mindfulness" to listOf(
        "fa-solid fa-brain"        to Icons.Default.Psychology,
        "fa-solid fa-heart"        to Icons.Default.Favorite,
        "fa-solid fa-spa"          to Icons.Default.Spa,
        "fa-regular fa-face-smile" to Icons.Default.SentimentSatisfied,
        "fa-solid fa-feather"      to Icons.Default.Edit,
        "fa-solid fa-leaf"         to Icons.Default.Eco,
        "fa-solid fa-om"           to Icons.Default.SelfImprovement,
        "fa-solid fa-cloud"        to Icons.Default.Cloud,
        "fa-solid fa-wind"         to Icons.Default.Air,
        "fa-regular fa-moon"       to Icons.Default.Bedtime,
        "fa-solid fa-dog"          to Icons.Default.Pets,
        "fa-solid fa-cat"          to Icons.Default.Pets,
        "fa-solid fa-dove"         to Icons.Default.Air,
        "fa-regular fa-sun"        to Icons.Default.WbSunny,
        "fa-solid fa-tree"         to Icons.Default.Park,
    ),
    "Study" to listOf(
        "fa-solid fa-book"           to Icons.Default.Book,
        "fa-solid fa-book-open"      to Icons.Default.MenuBook,
        "fa-solid fa-graduation-cap" to Icons.Default.School,
        "fa-solid fa-pencil"         to Icons.Default.Edit,
        "fa-solid fa-pen"            to Icons.Default.Create,
        "fa-solid fa-brain"          to Icons.Default.Psychology,
        "fa-solid fa-lightbulb"      to Icons.Default.Lightbulb,
        "fa-solid fa-microscope"     to Icons.Default.Biotech,
        "fa-solid fa-flask"          to Icons.Default.Science,
        "fa-solid fa-language"       to Icons.Default.Translate,
        "fa-solid fa-calculator"     to Icons.Default.Calculate,
        "fa-solid fa-glasses"        to Icons.Default.Visibility,
        "fa-solid fa-ruler"          to Icons.Default.Straighten,
        "fa-solid fa-chalkboard"     to Icons.Default.School,
        "fa-solid fa-school"         to Icons.Default.School,
    ),
    "Work" to listOf(
        "fa-solid fa-briefcase"      to Icons.Default.Work,
        "fa-solid fa-laptop"         to Icons.Default.Laptop,
        "fa-solid fa-computer"       to Icons.Default.Computer,
        "fa-solid fa-clock"          to Icons.Default.AccessTime,
        "fa-solid fa-calendar-check" to Icons.Default.EventAvailable,
        "fa-solid fa-chart-line"     to Icons.Default.TrendingUp,
        "fa-solid fa-chart-simple"   to Icons.Default.BarChart,
        "fa-solid fa-envelope"       to Icons.Default.Email,
        "fa-solid fa-users"          to Icons.Default.Group,
        "fa-solid fa-mug-hot"        to Icons.Default.LocalCafe,
        "fa-solid fa-building"       to Icons.Default.Business,
        "fa-solid fa-phone"          to Icons.Default.Phone,
        "fa-solid fa-file-lines"     to Icons.Default.Description,
        "fa-solid fa-folder"         to Icons.Default.Folder,
        "fa-solid fa-print"          to Icons.Default.Print,
    )
)

// ─── Brand gradient matching CSS: linear-gradient(135deg, #ff2a00, #2a51ff) ──────────

val SteadeGradientBrush = Brush.linearGradient(
    colors = listOf(SteadeRed, SteadeBlue),
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
                modifier     = Modifier.fillMaxWidth(0.6f).align(Alignment.BottomEnd),
                contentScale = ContentScale.Fit,
                alpha        = 0.18f
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
    NavItem(Screen.Achievements, Icons.Default.EmojiEvents, "Achievements"),
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
