package com.TBN.steade.ui.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.TBN.steade.data.network.*
import com.TBN.steade.data.repository.SteadERepository
import kotlinx.coroutines.launch

class SteadEViewModel(application: Application) : AndroidViewModel(application) {

    val repository = SteadERepository(application)

    var isLoggedIn  by mutableStateOf(repository.isLoggedIn())
    var authError   by mutableStateOf<String?>(null)
    var authLoading by mutableStateOf(false)

    var currentUser  by mutableStateOf<ApiUser?>(null)

    val habits        = mutableStateListOf<ApiHabit>()
    var habitsLoading by mutableStateOf(false)
    var habitsError   by mutableStateOf<String?>(null)

    val goals        = mutableStateListOf<ApiGoal>()
    var goalsLoading by mutableStateOf(false)
    var goalsError   by mutableStateOf<String?>(null)

    var statistics   by mutableStateOf<ApiStatistics?>(null)

    val achievements        = mutableStateListOf<ApiAchievement>()
    var achievementsLoading by mutableStateOf(false)

    init { if (isLoggedIn) loadAllData() }

    private fun loadAllData() {
        loadUser(); loadHabits(); loadGoals(); loadStatistics(); loadAchievements()
    }

    // ── Auth ──────────────────────────────────────────────────────────────────
    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            authLoading = true; authError = null
            repository.login(email, password).fold(
                onSuccess = { body ->
                    authLoading = false
                    if (body.success) { isLoggedIn = true; currentUser = body.user; loadAllData(); onSuccess() }
                    else authError = body.errors?.values?.flatten()?.joinToString("\n") ?: body.message ?: "Login failed"
                },
                onFailure = { authLoading = false; authError = it.message }
            )
        }
    }

    fun register(name: String, username: String, email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            authLoading = true; authError = null
            repository.register(name, username, email, password).fold(
                onSuccess = { body ->
                    authLoading = false
                    if (body.success) { isLoggedIn = true; currentUser = body.user; loadAllData(); onSuccess() }
                    else authError = body.errors?.values?.flatten()?.joinToString("\n") ?: body.message ?: "Registration failed"
                },
                onFailure = { authLoading = false; authError = it.message }
            )
        }
    }

    fun logout(onDone: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            isLoggedIn = false; currentUser = null
            habits.clear(); goals.clear(); achievements.clear(); statistics = null
            onDone()
        }
    }

    // ── User ──────────────────────────────────────────────────────────────────
    fun loadUser() { viewModelScope.launch { repository.getUser().onSuccess { currentUser = it } } }
    fun getDisplayName(): String { val n = currentUser?.name; return if (!n.isNullOrBlank()) n else repository.getUserName() }
    fun getDisplayEmail(): String { val e = currentUser?.email; return if (!e.isNullOrBlank()) e else repository.getUserEmail() }

    // ── Habits ────────────────────────────────────────────────────────────────
    fun loadHabits() {
        viewModelScope.launch {
            habitsLoading = true; habitsError = null
            repository.getHabits().fold(
                onSuccess = { habits.clear(); habits.addAll(it) },
                onFailure = { habitsError = it.message }
            )
            habitsLoading = false
        }
    }

    fun createHabit(name: String, description: String, category: String, frequency: String, icon: String) {
        viewModelScope.launch {
            repository.createHabit(CreateHabitRequest(name, description, category, frequency.lowercase(), 1, icon))
                .onSuccess { habits.add(0, it) }
        }
    }

    fun deleteHabit(habit: ApiHabit) {
        viewModelScope.launch { repository.deleteHabit(habit.id).onSuccess { habits.remove(habit) } }
    }

    // ── Goals ─────────────────────────────────────────────────────────────────
    fun loadGoals() {
        viewModelScope.launch {
            goalsLoading = true; goalsError = null
            repository.getGoals().fold(
                onSuccess = { goals.clear(); goals.addAll(it) },
                onFailure = { goalsError = it.message }
            )
            goalsLoading = false
        }
    }

    fun createGoal(name: String, deadline: String, description: String = "") {
        viewModelScope.launch {
            repository.createGoal(name, deadline, description).onSuccess { goals.add(0, it) }
        }
    }

    fun deleteGoal(goal: ApiGoal) {
        viewModelScope.launch { repository.deleteGoal(goal.id).onSuccess { goals.remove(goal) } }
    }

    // ── Statistics ────────────────────────────────────────────────────────────
    fun loadStatistics() {
        viewModelScope.launch { repository.getStatistics().onSuccess { statistics = it } }
    }

    // ── Achievements ──────────────────────────────────────────────────────────
    fun loadAchievements() {
        viewModelScope.launch {
            achievementsLoading = true
            repository.getAchievements().fold(
                onSuccess = { achievements.clear(); achievements.addAll(it) },
                onFailure = { /* keep fallback list */ }
            )
            achievementsLoading = false
        }
    }
}
