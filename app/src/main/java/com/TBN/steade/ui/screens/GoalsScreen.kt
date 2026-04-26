package com.TBN.steade.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.TBN.steade.data.network.ApiGoal
import com.TBN.steade.ui.components.BottomNavBar
import com.TBN.steade.ui.components.MainGradientBackground
import com.TBN.steade.ui.theme.SteadeNavyBlue
import com.TBN.steade.ui.viewmodel.SteadEViewModel

@Composable
fun GoalsScreen(navController: NavController, viewModel: SteadEViewModel) {
    var showAddDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { viewModel.loadGoals() }

    if (showAddDialog) {
        AddGoalDialog(
            onDismiss    = { showAddDialog = false },
            onGoalAdded  = { name, deadline, desc ->
                viewModel.createGoal(name, deadline, desc)
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

            if (viewModel.goalsLoading) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
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
fun AddGoalDialog(onDismiss: () -> Unit, onGoalAdded: (String, String, String) -> Unit) {
    var name        by remember { mutableStateOf("") }
    var deadline    by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    AlertDialog(onDismissRequest = onDismiss,
        title = { Text("Add New Goal", fontWeight = FontWeight.Bold, color = SteadeNavyBlue) },
        text  = {
            Column {
                OutlinedTextField(value = name, onValueChange = { name = it },
                    label = { Text("Goal Name") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), colors = webTextFieldColors(), singleLine = true)
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(value = deadline, onValueChange = { deadline = it },
                    label = { Text("Deadline (YYYY-MM-DD, optional)") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), colors = webTextFieldColors(), singleLine = true)
                Spacer(Modifier.height(10.dp))
                OutlinedTextField(value = description, onValueChange = { description = it },
                    label = { Text("Description (optional)") }, modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp), colors = webTextFieldColors())
            }
        },
        confirmButton = {
            Button(onClick = { if (name.isNotBlank()) onGoalAdded(name, deadline, description) },
                colors = ButtonDefaults.buttonColors(containerColor = SteadeNavyBlue)) { Text("Add Goal") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
    )
}

@Composable
fun GoalCard(goal: ApiGoal, onDelete: (() -> Unit)? = null) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.15f))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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
