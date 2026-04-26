package com.TBN.steade.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.TBN.steade.data.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SteadERepository(context: Context) {

    private val api   = RetrofitClient.apiService
    private val gson  = Gson()
    private val prefs: SharedPreferences =
        context.getSharedPreferences("SteadEPrefs", Context.MODE_PRIVATE)

    fun saveToken(token: String)  = prefs.edit().putString("auth_token", token).apply()
    fun getToken(): String?       = prefs.getString("auth_token", null)
    fun clearToken()              = prefs.edit().remove("auth_token").apply()
    fun isLoggedIn(): Boolean     = getToken() != null
    fun saveUserName(n: String)   = prefs.edit().putString("user_name",  n).apply()
    fun getUserName(): String     = prefs.getString("user_name",  "User") ?: "User"
    fun saveUserEmail(e: String)  = prefs.edit().putString("user_email", e).apply()
    fun getUserEmail(): String    = prefs.getString("user_email", "") ?: ""
    private fun bearer()          = "Bearer ${getToken()}"

    private fun parseError(raw: String?): String {
        if (raw.isNullOrBlank()) return "Unknown error"
        return try {
            val p = gson.fromJson(raw, AuthResponse::class.java)
            p.errors?.values?.flatten()?.joinToString("\n") ?: p.message ?: raw
        } catch (_: Exception) { raw }
    }

    // ─── Auth ──────────────────────────────────────────────────────────────────────────────────
    suspend fun login(email: String, password: String): Result<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                val r = api.login(LoginRequest(email, password))
                val rawErr = if (!r.isSuccessful) r.errorBody()?.string() else null
                val body   = r.body()
                if (r.isSuccessful && body?.token != null) {
                    body.token.let { saveToken(it) }
                    body.user?.let { saveUserName(it.name); saveUserEmail(it.email) }
                    Result.success(body)
                } else Result.failure(Exception(parseError(rawErr)))
            } catch (e: Exception) { Result.failure(Exception("Network error: ${e.message}")) }
        }

    suspend fun register(name: String, username: String, email: String, password: String): Result<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                val r = api.register(RegisterRequest(name, username, email, password, password))
                val rawErr = if (!r.isSuccessful) r.errorBody()?.string() else null
                val body   = r.body()
                if ((r.code() == 200 || r.code() == 201) && body?.token != null) {
                    body.token.let { saveToken(it) }
                    body.user?.let { saveUserName(it.name); saveUserEmail(it.email) }
                    Result.success(body)
                } else Result.failure(Exception(parseError(rawErr)))
            } catch (e: Exception) { Result.failure(Exception("Network error: ${e.message}")) }
        }

    suspend fun logout(): Result<Unit> = withContext(Dispatchers.IO) {
        try { api.logout(bearer()) } catch (_: Exception) {}
        clearToken(); Result.success(Unit)
    }

    // ─── User ──────────────────────────────────────────────────────────────────────────────────
    suspend fun getUser(): Result<ApiUser> = withContext(Dispatchers.IO) {
        try {
            val r = api.getUser(bearer())
            if (r.isSuccessful && r.body() != null) {
                val u = r.body()!!
                saveUserName(u.name); saveUserEmail(u.email)
                Result.success(u)
            } else Result.failure(Exception("User error: ${r.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    // ─── Habits ────────────────────────────────────────────────────────────────────────────────
    suspend fun getHabits(): Result<List<ApiHabit>> = withContext(Dispatchers.IO) {
        try {
            val r = api.getHabits(bearer())
            if (r.isSuccessful) Result.success(r.body() ?: emptyList())
            else Result.failure(Exception("Habits error: ${r.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun createHabit(req: CreateHabitRequest): Result<ApiHabit> = withContext(Dispatchers.IO) {
        try {
            val r = api.createHabit(bearer(), req)
            if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
            else Result.failure(Exception("Create habit error: ${r.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun deleteHabit(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val r = api.deleteHabit(bearer(), id)
            if (r.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Delete habit error: ${r.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    // ─── Goals ──────────────────────────────────────────────────────────────────────────────────
    suspend fun getGoals(): Result<List<ApiGoal>> = withContext(Dispatchers.IO) {
        try {
            val r = api.getGoals(bearer())
            if (r.isSuccessful) Result.success(r.body() ?: emptyList())
            else Result.failure(Exception("Goals error: ${r.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    suspend fun createGoal(name: String, deadline: String, description: String): Result<ApiGoal> =
        withContext(Dispatchers.IO) {
            try {
                val r = api.createGoal(
                    bearer(),
                    CreateGoalRequest(
                        title = name,
                        deadline = deadline.ifBlank { null },
                        description = description.ifBlank { null }
                    )
                )
                if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
                else Result.failure(Exception("Create goal error: ${r.code()}"))
            } catch (e: Exception) { Result.failure(e) }
        }

    suspend fun deleteGoal(id: Int): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val r = api.deleteGoal(bearer(), id)
            if (r.isSuccessful) Result.success(Unit)
            else Result.failure(Exception("Delete goal error: ${r.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    // ─── Statistics ─────────────────────────────────────────────────────────────────────────────
    suspend fun getStatistics(): Result<ApiStatistics> = withContext(Dispatchers.IO) {
        try {
            val r = api.getStatistics(bearer())
            if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
            else Result.failure(Exception("Stats error: ${r.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    // ─── Achievements ──────────────────────────────────────────────────────────────────────────────
    suspend fun getAchievements(): Result<List<ApiAchievement>> = withContext(Dispatchers.IO) {
        try {
            val r = api.getAchievements(bearer())
            if (r.isSuccessful) Result.success(r.body() ?: emptyList())
            else Result.failure(Exception("Achievements error: ${r.code()}"))
        } catch (e: Exception) { Result.failure(e) }
    }

    // ─── Habit Completions ───────────────────────────────────────────────────────────────────────────
    suspend fun logHabitCompletion(habitId: Int, quantity: Int = 1): Result<HabitCompletionResponse> =
        withContext(Dispatchers.IO) {
            try {
                val r = api.logHabitCompletion(bearer(), HabitCompletionRequest(habitId, quantity))
                if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
                else Result.failure(Exception("Log completion error: ${r.code()}"))
            } catch (e: Exception) { Result.failure(e) }
        }

    suspend fun removeHabitCompletion(habitId: Int, amount: Int = 1): Result<HabitCompletionResponse> =
        withContext(Dispatchers.IO) {
            try {
                val r = api.removeHabitCompletion(bearer(), habitId, amount)
                if (r.isSuccessful && r.body() != null) Result.success(r.body()!!)
                else Result.failure(Exception("Remove completion error: ${r.code()}"))
            } catch (e: Exception) { Result.failure(e) }
        }
}
