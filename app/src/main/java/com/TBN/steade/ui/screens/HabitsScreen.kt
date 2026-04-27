package com.TBN.steade.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.data.network.ApiHabit
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.components.habitCategoryIcons
import com.TBN.steade.ui.components.habitIconToMaterialIcon
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.viewmodel.SteadEViewModel

@Composable
fun HabitsScreen(navController: NavController, viewModel: SteadEViewModel) {
    var showDialog    by remember { mutableStateOf(false) }
    var habitToEdit   by remember { mutableStateOf<ApiHabit?>(null) }

    LaunchedEffect(Unit) { viewModel.loadHabits() }

    if (showDialog) {
        HabitDialog(
            habitToEdit  = habitToEdit,
            onDismiss    = { showDialog = false; habitToEdit = null },
            onConfirm    = { name, desc, cat, freq, icon, targetCount, unit, scheduledDays ->
                if (habitToEdit != null) {
                    viewModel.updateHabit(habitToEdit!!, name, desc, cat, freq, icon, targetCount, unit, scheduledDays)
                } else {
                    viewModel.createHabit(name, desc, cat, freq, icon, targetCount, unit, scheduledDays)
                }
                showDialog  = false
                habitToEdit = null
            }
        )
    }

    MainGradientBackground(showShadow = true) {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Habits", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                FloatingActionButton(
                    onClick = { habitToEdit = null; showDialog = true },
                    containerColor = Color.White, contentColor = SteadeNavyBlue,
                    shape = RoundedCornerShape(14.dp), modifier = Modifier.size(48.dp)
                ) {
                    Icon(Icons.Default.Add, null)
                }
            }
            Spacer(Modifier.height(16.dp))

            when {
                viewModel.habitsLoading -> Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator(color = Color.White) }

                viewModel.habitsError != null -> Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            viewModel.habitsError!!, color = Color.White.copy(alpha = 0.7f),
                            fontSize = 14.sp, textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = { viewModel.loadHabits() },
                            colors  = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = SteadeNavyBlue)
                        ) { Text("Retry") }
                    }
                }

                else -> LazyColumn(modifier = Modifier.weight(1f)) {
                    items(viewModel.habits) { habit ->
                        HabitCard(
                            habit    = habit,
                            onEdit   = { habitToEdit = habit; showDialog = true },
                            onDelete = { viewModel.deleteHabit(habit) }
                        )
                        Spacer(Modifier.height(10.dp))
                    }
                    if (viewModel.habits.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier.fillMaxWidth().padding(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    "No habits yet.\nTap + to create your first habit!",
                                    color = Color.White.copy(alpha = 0.6f),
                                    fontSize = 15.sp, textAlign = TextAlign.Center
                                )
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
fun HabitDialog(
    habitToEdit: ApiHabit?,
    onDismiss:   () -> Unit,
    onConfirm:   (String, String, String, String, String, Int, String, List<Int>?) -> Unit
) {
    val isEdit      = habitToEdit != null
    val categories  = listOf("Nutrition", "Fitness", "Mindfulness", "Study", "Work")
    val frequencies = listOf("daily", "weekly", "monthly")
    val units       = listOf("times", "days", "km", "books", "minutes", "custom")
    val weekDays    = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")

    var name         by remember { mutableStateOf(habitToEdit?.name        ?: "") }
    var description  by remember { mutableStateOf(habitToEdit?.description ?: "") }
    var selectedCat  by remember { mutableStateOf(habitToEdit?.category    ?: "Fitness") }
    var frequency    by remember { mutableStateOf(habitToEdit?.frequency   ?: "daily") }
    var targetCount  by remember { mutableStateOf((habitToEdit?.targetCount ?: 1).toString()) }

    // Detect custom unit
    val presetUnits = listOf("times", "days", "km", "books", "minutes")
    val rawUnit     = habitToEdit?.unit ?: "times"
    var selectedUnit by remember { mutableStateOf(if (rawUnit in presetUnits) rawUnit else "custom") }
    var customUnit   by remember { mutableStateOf(if (rawUnit in presetUnits) "" else rawUnit) }

    // Scheduled days
    val initDays: Set<Int> = habitToEdit?.scheduledDays
        ?.mapNotNull { d -> when (d) { is Number -> d.toInt(); is String -> d.toIntOrNull(); else -> null } }
        ?.toSet() ?: emptySet()
    var selectedDays by remember { mutableStateOf(initDays) }

    var catExpanded  by remember { mutableStateOf(false) }
    var freqExpanded by remember { mutableStateOf(false) }
    var unitExpanded by remember { mutableStateOf(false) }

    val iconOptions      = habitCategoryIcons[selectedCat] ?: emptyList()
    var selectedIconFa   by remember(selectedCat) {
        val initIcon = habitToEdit?.icon ?: (iconOptions.firstOrNull()?.first ?: "fa-solid fa-star")
        mutableStateOf(initIcon)
    }

    AlertDialog(
        containerColor   = Color.White,
        onDismissRequest = onDismiss,
        title = {
            Text(
                if (isEdit) "Edit Habit" else "Create New Habit",
                fontWeight = FontWeight.Bold, color = SteadeNavyBlue
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                // Name
                OutlinedTextField(
                    value = name, onValueChange = { name = it },
                    label = { Text("Habit Name") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), colors = webTextFieldColors(), singleLine = true
                )
                Spacer(Modifier.height(10.dp))

                // Description
                OutlinedTextField(
                    value = description, onValueChange = { description = it },
                    label = { Text("Description") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), colors = webTextFieldColors(), minLines = 2
                )
                Spacer(Modifier.height(10.dp))

                // Category
                ExposedDropdownMenuBox(expanded = catExpanded, onExpandedChange = { catExpanded = !catExpanded }) {
                    OutlinedTextField(
                        value = selectedCat, onValueChange = {}, readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(catExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp), colors = webTextFieldColors()
                    )
                    ExposedDropdownMenu(expanded = catExpanded, onDismissRequest = { catExpanded = false }) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = { selectedCat = cat; catExpanded = false; selectedDays = emptySet() }
                            )
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))

                // Frequency
                ExposedDropdownMenuBox(expanded = freqExpanded, onExpandedChange = { freqExpanded = !freqExpanded }) {
                    OutlinedTextField(
                        value = frequency.replaceFirstChar { it.uppercase() }, onValueChange = {}, readOnly = true,
                        label = { Text("Frequency") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(freqExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp), colors = webTextFieldColors()
                    )
                    ExposedDropdownMenu(expanded = freqExpanded, onDismissRequest = { freqExpanded = false }) {
                        frequencies.forEach { f ->
                            DropdownMenuItem(
                                text = { Text(f.replaceFirstChar { c -> c.uppercase() }) },
                                onClick = { frequency = f; freqExpanded = false; selectedDays = emptySet() }
                            )
                        }
                    }
                }

                // Scheduled Days
                if (frequency != "daily") {
                    Spacer(Modifier.height(10.dp))
                    Text("Scheduled Days", fontSize = 13.sp, color = SteadeNavyBlue, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    if (frequency == "weekly") {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            weekDays.forEachIndexed { idx, label ->
                                val sel = idx in selectedDays
                                FilterChip(
                                    selected = sel,
                                    onClick  = { selectedDays = if (sel) selectedDays - idx else selectedDays + idx },
                                    label    = { Text(label, fontSize = 11.sp) },
                                    colors   = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = SteadeNavyBlue,
                                        selectedLabelColor     = Color.White
                                    )
                                )
                            }
                        }
                    } else {
                        (1..31).chunked(7).forEach { row ->
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                row.forEach { day ->
                                    val sel = day in selectedDays
                                    FilterChip(
                                        selected = sel,
                                        onClick  = { selectedDays = if (sel) selectedDays - day else selectedDays + day },
                                        label    = { Text("$day", fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f),
                                        colors   = FilterChipDefaults.filterChipColors(
                                            selectedContainerColor = SteadeNavyBlue,
                                            selectedLabelColor     = Color.White
                                        )
                                    )
                                }
                            }
                            Spacer(Modifier.height(2.dp))
                        }
                    }
                }
                Spacer(Modifier.height(10.dp))

                // Target Count
                OutlinedTextField(
                    value = targetCount,
                    onValueChange = { if (it.all { c -> c.isDigit() } && it.length <= 4) targetCount = it },
                    label = { Text("Target Count") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), colors = webTextFieldColors(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), singleLine = true
                )
                Spacer(Modifier.height(10.dp))

                // Unit
                ExposedDropdownMenuBox(expanded = unitExpanded, onExpandedChange = { unitExpanded = !unitExpanded }) {
                    OutlinedTextField(
                        value = selectedUnit.replaceFirstChar { it.uppercase() }, onValueChange = {}, readOnly = true,
                        label = { Text("Unit") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(unitExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp), colors = webTextFieldColors()
                    )
                    ExposedDropdownMenu(expanded = unitExpanded, onDismissRequest = { unitExpanded = false }) {
                        units.forEach { u ->
                            DropdownMenuItem(
                                text = { Text(u.replaceFirstChar { c -> c.uppercase() }) },
                                onClick = { selectedUnit = u; unitExpanded = false }
                            )
                        }
                    }
                }
                if (selectedUnit == "custom") {
                    Spacer(Modifier.height(10.dp))
                    OutlinedTextField(
                        value = customUnit, onValueChange = { customUnit = it },
                        label = { Text("Custom Unit (e.g. pages, glasses)") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp), colors = webTextFieldColors(), singleLine = true
                    )
                }
                Spacer(Modifier.height(10.dp))

                // Icon picker
                Text("Icon", fontSize = 13.sp, color = SteadeNavyBlue, fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(6.dp))
                iconOptions.chunked(5).forEach { row ->
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        row.forEach { (faClass, materialIcon) ->
                            Box(
                                modifier = Modifier.size(44.dp).clip(CircleShape)
                                    .background(if (selectedIconFa == faClass) SteadeNavyBlue.copy(alpha = 0.15f) else Color.Transparent)
                                    .clickable { selectedIconFa = faClass },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(materialIcon, contentDescription = null, tint = SteadeNavyBlue, modifier = Modifier.size(24.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        val unit = if (selectedUnit == "custom") customUnit.ifBlank { "times" } else selectedUnit
                        val days = if (frequency == "daily") null else selectedDays.sorted().ifEmpty { null }
                        onConfirm(name, description, selectedCat, frequency, selectedIconFa, targetCount.toIntOrNull() ?: 1, unit, days)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = SteadeNavyBlue)
            ) { Text(if (isEdit) "Edit Habit" else "Create") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun HabitCard(habit: ApiHabit, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().alpha(if (habit.isActive) 1f else 0.55f),
        shape  = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))
    ) {
        Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                habitIconToMaterialIcon(habit.icon),
                contentDescription = null,
                tint     = Color.White,
                modifier = Modifier.size(28.dp)
            )
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(habit.name, color = Color.White, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                if (!habit.description.isNullOrBlank())
                    Text(habit.description, color = Color.White.copy(alpha = 0.65f), fontSize = 13.sp, maxLines = 1)
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.padding(top = 4.dp)) {
                    if (!habit.category.isNullOrBlank())  HabitChip(habit.category)
                    if (!habit.frequency.isNullOrBlank())  HabitChip(habit.frequency.replaceFirstChar { it.uppercase() })
                    if (habit.isCompletedToday)            HabitChip("Done", icon = Icons.Default.Check)
                }
            }
            // Edit button
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White.copy(alpha = 0.75f))
            }
            // Delete button
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White.copy(alpha = 0.55f))
            }
        }
    }
}

@Composable
fun HabitChip(text: String, icon: ImageVector? = null) {
    Surface(shape = RoundedCornerShape(20.dp), color = Color.White.copy(alpha = 0.15f)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.8f), modifier = Modifier.size(10.dp))
                Spacer(Modifier.width(3.dp))
            }
            Text(text, color = Color.White.copy(alpha = 0.8f), fontSize = 10.sp)
        }
    }
}
