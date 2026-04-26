package com.TBN.steade.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.data.network.ApiHabit
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.components.habitIconToEmoji
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.viewmodel.SteadEViewModel

@Composable
fun HabitsScreen(navController: NavController, viewModel: SteadEViewModel) {
    var showAddDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { viewModel.loadHabits() }

    if (showAddDialog) {
        AddHabitDialog(
            onDismiss    = { showAddDialog = false },
            onHabitAdded = { name, desc, cat, freq, icon ->
                viewModel.createHabit(name, desc, cat, freq, icon)
                showAddDialog = false
            }
        )
    }

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(Modifier.height(32.dp))
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("My Habits", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                FloatingActionButton(onClick = { showAddDialog = true },
                    containerColor = Color.White, contentColor = SteadeNavyBlue,
                    shape = RoundedCornerShape(14.dp), modifier = Modifier.size(48.dp)) {
                    Icon(Icons.Default.Add, null)
                }
            }
            Spacer(Modifier.height(16.dp))

            when {
                viewModel.habitsLoading -> Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
                viewModel.habitsError != null -> Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(viewModel.habitsError!!, color = Color.White.copy(alpha = 0.7f), fontSize = 14.sp, textAlign = TextAlign.Center)
                        Spacer(Modifier.height(12.dp))
                        Button(onClick = { viewModel.loadHabits() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = SteadeNavyBlue)) { Text("Retry") }
                    }
                }
                else -> LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.habits) { habit ->
                        HabitCard(habit = habit, onDelete = { viewModel.deleteHabit(habit) })
                        Spacer(Modifier.height(10.dp))
                    }
                    if (viewModel.habits.isEmpty()) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                                Text("No habits yet.\nTap + to create your first habit!",
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
fun AddHabitDialog(onDismiss: () -> Unit, onHabitAdded: (String, String, String, String, String) -> Unit) {
    var name           by remember { mutableStateOf("") }
    var description    by remember { mutableStateOf("") }
    var selectedCat    by remember { mutableStateOf("General") }
    var frequency      by remember { mutableStateOf("daily") }
    var selectedIcon   by remember { mutableStateOf("star") }
    var freqExpanded   by remember { mutableStateOf(false) }
    var catExpanded    by remember { mutableStateOf(false) }

    val categories  = listOf("General","Fitness","Health","Nutrition","Study","Work","Mindfulness")
    val frequencies = listOf("daily","weekly","monthly")
    val icons       = listOf("⭐","🏋️","🧘","🍎","📖","💻","💧","🏃","🌱","🎯","💪","🛌")

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text("Create New Habit", fontWeight = FontWeight.Bold, color = SteadeNavyBlue) },
        text  = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                OutlinedTextField(value = name, onValueChange = { name = it },
                    label = { Text("Habit Name") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), colors = webTextFieldColors(), singleLine = true)
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(value = description, onValueChange = { description = it },
                    label = { Text("Description") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), colors = webTextFieldColors())
                Spacer(Modifier.height(10.dp))

                // Category
                ExposedDropdownMenuBox(expanded = catExpanded, onExpandedChange = { catExpanded = !catExpanded }) {
                    OutlinedTextField(value = selectedCat, onValueChange = {}, readOnly = true,
                        label = { Text("Category") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(catExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(), shape = RoundedCornerShape(8.dp), colors = webTextFieldColors())
                    ExposedDropdownMenu(expanded = catExpanded, onDismissRequest = { catExpanded = false }) {
                        categories.forEach { DropdownMenuItem(text = { Text(it) }, onClick = { selectedCat = it; catExpanded = false }) }
                    }
                }
                Spacer(Modifier.height(10.dp))

                // Frequency
                ExposedDropdownMenuBox(expanded = freqExpanded, onExpandedChange = { freqExpanded = !freqExpanded }) {
                    OutlinedTextField(value = frequency.replaceFirstChar { it.uppercase() }, onValueChange = {}, readOnly = true,
                        label = { Text("Frequency") }, trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(freqExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(), shape = RoundedCornerShape(8.dp), colors = webTextFieldColors())
                    ExposedDropdownMenu(expanded = freqExpanded, onDismissRequest = { freqExpanded = false }) {
                        frequencies.forEach { DropdownMenuItem(text = { Text(it.replaceFirstChar { c -> c.uppercase() }) }, onClick = { frequency = it; freqExpanded = false }) }
                    }
                }
                Spacer(Modifier.height(10.dp))

                // Icon picker
                Text("Icon", fontSize = 13.sp, color = SteadeNavyBlue, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(6.dp))
                androidx.compose.foundation.lazy.grid.LazyVerticalGrid(
                    columns = androidx.compose.foundation.lazy.grid.GridCells.Fixed(6),
                    modifier = Modifier.height(80.dp), userScrollEnabled = false) {
                    items(icons.size) { idx ->
                        val ic = icons[idx]
                        Box(modifier = Modifier.size(36.dp).clip(CircleShape)
                            .background(if (selectedIcon == ic) SteadeNavyBlue.copy(alpha = 0.2f) else Color.Transparent)
                            .clickable { selectedIcon = ic }, contentAlignment = Alignment.Center) {
                            Text(ic, fontSize = 20.sp)
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { if (name.isNotBlank()) onHabitAdded(name, description, selectedCat, frequency, selectedIcon) },
                colors = ButtonDefaults.buttonColors(containerColor = SteadeNavyBlue)) { Text("Create") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun HabitCard(habit: ApiHabit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth().alpha(if (habit.isActive) 1f else 0.55f),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(habitIconToEmoji(habit.icon), fontSize = 26.sp)
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(habit.name, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                if (!habit.description.isNullOrBlank())
                    Text(habit.description, color = Color.White.copy(alpha = 0.65f), fontSize = 13.sp, maxLines = 1)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                    if (!habit.category.isNullOrBlank()) Chip(habit.category)
                    if (!habit.frequency.isNullOrBlank()) Chip(habit.frequency.replaceFirstChar { it.uppercase() })
                    if (habit.isCompletedToday) Chip("✓ Done")
                }
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, null, tint = Color.White.copy(alpha = 0.55f))
            }
        }
    }
}

@Composable
fun Chip(text: String) {
    Surface(shape = RoundedCornerShape(20.dp), color = Color.White.copy(alpha = 0.15f)) {
        Text(text, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp))
    }
}
