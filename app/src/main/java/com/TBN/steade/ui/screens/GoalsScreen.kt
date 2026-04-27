package com.TBN.steade.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.TBN.steade.data.network.ApiGoal
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.viewmodel.SteadEViewModel

// Maps every Material icon name used by the web app to its Compose ImageVector.
// When the API returns goal.icon = "fitness_center" this map resolves it for display.
private val materialIconMap: Map<String, ImageVector> = mapOf(
    // Nutrition
    "restaurant"            to Icons.Default.Restaurant,
    "local_dining"          to Icons.Default.LocalDining,
    "coffee"                to Icons.Default.Coffee,
    "local_cafe"            to Icons.Default.LocalCafe,
    "kitchen"               to Icons.Default.Kitchen,
    "cake"                  to Icons.Default.Cake,
    "water_drop"            to Icons.Default.WaterDrop,
    "wine_bar"              to Icons.Default.WineBar,
    "fastfood"              to Icons.Default.Fastfood,
    "free_breakfast"        to Icons.Default.FreeBreakfast,
    "emoji_food_beverage"   to Icons.Default.EmojiFoodBeverage,
    "set_meal"              to Icons.Default.SetMeal,
    "soup_kitchen"          to Icons.Default.SoupKitchen,
    "food_bank"             to Icons.Default.FoodBank,
    "ice_cream"             to Icons.Default.LocalDrink,
    // Fitness
    "fitness_center"        to Icons.Default.FitnessCenter,
    "directions_run"        to Icons.Default.DirectionsRun,
    "directions_walk"       to Icons.Default.DirectionsWalk,
    "pedal_bike"            to Icons.Default.PedalBike,
    "monitor_heart"         to Icons.Default.MonitorHeart,
    "local_fire_department" to Icons.Default.LocalFireDepartment,
    "timer"                 to Icons.Default.Timer,
    "nordic_walking"        to Icons.Default.NordicWalking,
    "pool"                  to Icons.Default.Pool,
    "hiking"                to Icons.Default.Hiking,
    "sports_soccer"         to Icons.Default.SportsSoccer,
    "sports_basketball"     to Icons.Default.SportsBasketball,
    "sports"                to Icons.Default.Sports,
    "electric_bike"         to Icons.Default.ElectricBike,
    "accessibility_new"     to Icons.Default.AccessibilityNew,
    "sprint"                to Icons.Default.DirectionsBike,
    // Mindfulness
    "psychology"            to Icons.Default.Psychology,
    "favorite"              to Icons.Default.Favorite,
    "spa"                   to Icons.Default.Spa,
    "sentiment_satisfied"   to Icons.Default.SentimentSatisfied,
    "cloud"                 to Icons.Default.Cloud,
    "air"                   to Icons.Default.Air,
    "bedtime"               to Icons.Default.Bedtime,
    "wb_sunny"              to Icons.Default.WbSunny,
    "park"                  to Icons.Default.Park,
    "nature"                to Icons.Default.Nature,
    "pets"                  to Icons.Default.Pets,
    "self_improvement"      to Icons.Default.SelfImprovement,
    "nightlight"            to Icons.Default.Nightlight,
    "eco"                   to Icons.Default.Eco,
    "brightness_5"          to Icons.Default.Brightness5,
    // Study
    "menu_book"             to Icons.Default.MenuBook,
    "book"                  to Icons.Default.Book,
    "school"                to Icons.Default.School,
    "edit"                  to Icons.Default.Edit,
    "lightbulb"             to Icons.Default.Lightbulb,
    "biotech"               to Icons.Default.Biotech,
    "science"               to Icons.Default.Science,
    "calculate"             to Icons.Default.Calculate,
    "translate"             to Icons.Default.Translate,
    "visibility"            to Icons.Default.Visibility,
    "auto_stories"          to Icons.Default.AutoStories,
    "quiz"                  to Icons.Default.Quiz,
    "history_edu"           to Icons.Default.HistoryEdu,
    "laptop_chromebook"     to Icons.Default.LaptopChromebook,
    "straighten"            to Icons.Default.Straighten,
    // Work
    "work"                  to Icons.Default.Work,
    "laptop"                to Icons.Default.Laptop,
    "computer"              to Icons.Default.Computer,
    "schedule"              to Icons.Default.Schedule,
    "event_available"       to Icons.Default.EventAvailable,
    "trending_up"           to Icons.Default.TrendingUp,
    "bar_chart"             to Icons.Default.BarChart,
    "email"                 to Icons.Default.Email,
    "group"                 to Icons.Default.Group,
    "business"              to Icons.Default.Business,
    "phone"                 to Icons.Default.Phone,
    "description"           to Icons.Default.Description,
    "folder"                to Icons.Default.Folder,
    "print"                 to Icons.Default.Print,
)

// Icon picker list — same names as the web app's categoryIcons, in a flat list for the dialog grid.
private val goalIcons: List<Pair<String, ImageVector>> = materialIconMap.entries
    .map { it.key to it.value }

@Composable
fun GoalsScreen(navController: NavController, viewModel: SteadEViewModel) {
    var showAddDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { viewModel.loadGoals() }

    if (showAddDialog) {
        AddGoalDialog(
            onDismiss = { showAddDialog = false },
            onGoalAdded = { name, deadline, desc, icon ->
                viewModel.createGoal(name, deadline, desc, icon)
                showAddDialog = false
            }
        )
    }

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("My Goals", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                FloatingActionButton(onClick = { showAddDialog = true },
                    containerColor = Color.White, contentColor = SteadeNavyBlue,
                    shape = RoundedCornerShape(14.dp), modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Default.Add, null)
                }
            }
            Spacer(Modifier.height(16.dp))

            when {
                viewModel.goalsLoading -> Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
                viewModel.goalsError != null -> Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(viewModel.goalsError!!, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { viewModel.loadGoals() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = SteadeNavyBlue)) { Text("Retry") }
                    }
                }
                else -> LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.goals) { goal ->
                        GoalCard(goal = goal, onDelete = { viewModel.deleteGoal(goal) })
                        Spacer(Modifier.height(12.dp))
                    }
                    if (viewModel.goals.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                                Text("No goals yet.\nTap + to set your first goal!",
                                    color = Color.White.copy(alpha = 0.6f), fontSize = 15.sp, textAlign = TextAlign.Center)
                            }
                        }
                    }
                }
            }
            BottomNavBar(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoalDialog(onDismiss: () -> Unit, onGoalAdded: (String, String, String, String) -> Unit) {
    var name by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedIcon by remember { mutableStateOf(goalIcons.first { it.first == "sports" }) }

    Dialog(onDismissRequest = onDismiss) {
        Card(shape = RoundedCornerShape(16.dp), colors = CardDefaults.cardColors(containerColor = Color.White)) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text("Add New Goal", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SteadeNavyBlue)
                Spacer(Modifier.height(20.dp))

                OutlinedTextField(
                    value = name, onValueChange = { name = it },
                    label = { Text("Goal Name") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), singleLine = true, colors = webTextFieldColors()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = deadline, onValueChange = { deadline = it },
                    label = { Text("Deadline (YYYY-MM-DD)") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), singleLine = true, colors = webTextFieldColors()
                )
                Spacer(Modifier.height(12.dp))

                OutlinedTextField(
                    value = description, onValueChange = { description = it },
                    label = { Text("Description") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), minLines = 2, colors = webTextFieldColors()
                )
                Spacer(Modifier.height(16.dp))

                Text("Icon", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SteadeNavyBlue)
                Spacer(Modifier.height(8.dp))

                Column(modifier = Modifier
                    .heightIn(max = 200.dp)
                    .verticalScroll(rememberScrollState())) {
                    goalIcons.chunked(5).forEach { row ->
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            row.forEach { (key, icon) ->
                                val isSelected = selectedIcon.first == key
                                Box(
                                    modifier = Modifier
                                        .size(44.dp)
                                        .clip(CircleShape)
                                        .background(if (isSelected) SteadeNavyBlue.copy(alpha = 0.15f) else Color.Transparent)
                                        .border(if (isSelected) 2.dp else 0.dp, SteadeNavyBlue, CircleShape)
                                        .clickable { selectedIcon = key to icon },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(icon, contentDescription = key, tint = SteadeNavyBlue, modifier = Modifier.size(24.dp))
                                }
                            }
                            repeat(5 - row.size) { Spacer(Modifier.size(44.dp)) }
                        }
                        Spacer(Modifier.height(4.dp))
                    }
                }

                Spacer(Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) { Text("Cancel", color = SteadeNavyBlue) }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = { if (name.isNotBlank()) onGoalAdded(name, deadline, description, selectedIcon.first) },
                        colors = ButtonDefaults.buttonColors(containerColor = SteadeNavyBlue)
                    ) { Text("Add Goal") }
                }
            }
        }
    }
}

@Composable
fun GoalCard(goal: ApiGoal, onDelete: (() -> Unit)? = null) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {

                // Icon box — mirrors the web app's .goal-icon card element
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SteadeNavyBlue.copy(alpha = 0.3f))
                        .border(2.dp, SteadeNavyBlue, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = materialIconMap[goal.icon] ?: Icons.Default.Sports,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(goal.displayName, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    if (!goal.deadline.isNullOrBlank())
                        Text("Deadline: ${goal.deadline}", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                    if (!goal.status.isNullOrBlank())
                        Text("Status: ${goal.status}", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                }

                if (onDelete != null) {
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, null, tint = Color.White.copy(alpha = 0.55f))
                    }
                }
            }

            if (!goal.description.isNullOrBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(goal.description, color = Color.White.copy(alpha = 0.7f), fontSize = 13.sp)
            }

            if (goal.targetValue > 0) {
                Spacer(Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${goal.currentValue.toInt()} / ${goal.targetValue.toInt()}",
                        color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                    Text("${(goal.progress * 100).toInt()}%", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                }
                Spacer(Modifier.height(6.dp))
                LinearProgressIndicator(progress = { goal.progress },
                    modifier = Modifier.fillMaxWidth().height(8.dp),
                    color = Color.White, trackColor = Color.White.copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round)
            }
        }
    }
}
