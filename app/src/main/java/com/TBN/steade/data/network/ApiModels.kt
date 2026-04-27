package com.TBN.steade.data.network

import com.google.gson.annotations.SerializedName

// Auth models

data class LoginRequest(val email: String, val password: String)

data class RegisterRequest(
    val name: String, val username: String, val email: String, val password: String,
    @SerializedName("password_confirmation") val passwordConfirmation: String
)

data class AuthResponse(
    val token: String? = null, val user: ApiUser? = null,
    val message: String? = null, val errors: Map<String, List<String>>? = null
) { val success: Boolean get() = token != null }

// User model

data class ApiUser(
    val id: Int = 0, val name: String = "", val username: String? = null, val email: String = "",
    @SerializedName("profile_picture")      val profilePicture: String? = null,
    val gender: String? = null, val birthdate: String? = null,
    val weight: Double? = null, val height: Double? = null,
    @SerializedName("sleep_time")           val sleepTime: String? = null,
    @SerializedName("wake_time")            val wakeTime: String? = null,
    @SerializedName("user_goal")            val userGoal: String? = null,
    @SerializedName("preferred_categories") val preferredCategories: Any? = null,
    @SerializedName("is_admin")             val isAdmin: Boolean = false,
    @SerializedName("created_at")           val createdAt: String? = null
) {
    fun preferredCategoriesText(): String? = when (preferredCategories) {
        is List<*> -> preferredCategories.joinToString(", ")
        is String  -> preferredCategories
        else       -> null
    }
}

// Habits
// MySQL returns is_active as 1/0 so we store it as Any? and convert.

data class ApiHabit(
    val id: Int = 0,
    val name: String = "",
    val description: String? = null,
    val category: String? = null,
    val frequency: String? = null,
    @SerializedName("target_count")    val targetCount: Int? = null,
    val icon: String? = null,
    val unit: String? = null,
    @SerializedName("is_active")       val isActiveRaw: Any? = 1,
    @SerializedName("completed_today") val completedToday: Int = 0,
    @SerializedName("goal_name")       val goalName: String? = null,
    @SerializedName("goal_id")         val goalId: Int? = null,
    @SerializedName("scheduled_days")  val scheduledDays: List<Any>? = null,
    @SerializedName("user_id")         val userId: Int? = null,
    @SerializedName("created_at")      val createdAt: String? = null
) {
    val isActive: Boolean
        get() = when (isActiveRaw) {
            is Boolean -> isActiveRaw
            is Number  -> isActiveRaw.toInt() != 0
            is String  -> isActiveRaw == "1" || isActiveRaw.equals("true", ignoreCase = true)
            else       -> true
        }
    val isCompletedToday: Boolean get() = completedToday > 0
}

data class CreateHabitRequest(
    val name: String,
    val description: String = "",
    val category: String = "Fitness",
    val frequency: String = "daily",
    @SerializedName("target_count")   val targetCount: Int = 1,
    val unit: String = "times",
    @SerializedName("scheduled_days") val scheduledDays: List<Int>? = null,
    val icon: String = "fa-solid fa-star",
    @SerializedName("goal_id")        val goalId: Int? = null
)

data class HabitResponse(val habit: ApiHabit? = null, val message: String? = null)

// Goals

data class ApiGoal(
    val id: Int = 0, val name: String = "", val title: String? = null,
    val description: String? = null, val deadline: String? = null,
    val icon: String = "sports",
    @SerializedName("target_value")  val targetValue: Float = 0f,
    @SerializedName("current_value") val currentValue: Float = 0f,
    val status: String? = null,
    @SerializedName("user_id")       val userId: Int? = null,
    @SerializedName("created_at")    val createdAt: String? = null
) {
    val progress: Float get() = if (targetValue > 0) (currentValue / targetValue).coerceIn(0f, 1f) else 0f
    val displayName: String get() = title ?: name
}

data class CreateGoalRequest(
    val title: String,
    val deadline: String? = null,
    val description: String? = null,
    val icon: String = "sports",
    val category: String = "General",
    @SerializedName("target_value") val targetValue: Int = 1,
    val unit: String = "times"
)

// Statistics
// dailyCompletions and categoryBreakdown come back as keyed JSON objects.

data class ApiStatistics(
    @SerializedName("total_habits")          val totalHabits: Int = 0,
    @SerializedName("active_habits")         val activeHabits: Int = 0,
    @SerializedName("current_streak")        val currentStreak: Int = 0,
    @SerializedName("longest_streak")        val longestStreak: Int = 0,
    @SerializedName("completions_this_week") val completionsThisWeek: Int = 0,
    @SerializedName("daily_completions")     val dailyCompletions: Map<String, Int> = emptyMap(),
    @SerializedName("category_breakdown")    val categoryBreakdown: Map<String, Int> = emptyMap()
)

// Achievements

data class ApiAchievement(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val icon: String = "",
    @SerializedName("achievement_type") val achievementType: String? = null,
    @SerializedName("is_unlocked")      val isUnlocked: Boolean = false,
    @SerializedName("unlocked_at")      val unlockedAt: String? = null,
    val progress: Float = 0f,
    @SerializedName("threshold_value")  val thresholdValue: Int = 0
)

// Habit completions

data class HabitCompletionRequest(
    @SerializedName("habit_id") val habitId: Int,
    val quantity: Int = 1
)

data class HabitCompletionResponse(
    val completed: Int = 0,
    val target: Int = 1,
    @SerializedName("daily_progress_percent") val dailyProgressPercent: Int = 0,
    @SerializedName("has_completion_today")   val hasCompletionToday: Boolean = false
)

data class GenericResponse(val message: String? = null)
