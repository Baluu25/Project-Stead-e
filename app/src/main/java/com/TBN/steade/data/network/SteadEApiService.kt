package com.TBN.steade.data.network

import retrofit2.Response
import retrofit2.http.*

interface SteadEApiService {

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>

    @POST("api/register")
    suspend fun register(@Body request: RegisterRequest): Response<AuthResponse>

    @POST("api/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<GenericResponse>

    // Returns the user object directly
    @GET("api/user")
    suspend fun getUser(@Header("Authorization") token: String): Response<ApiUser>

    // Returns a plain JSON array
    @GET("api/habits")
    suspend fun getHabits(@Header("Authorization") token: String): Response<List<ApiHabit>>

    @POST("api/habits")
    suspend fun createHabit(
        @Header("Authorization") token: String,
        @Body request: CreateHabitRequest
    ): Response<ApiHabit>

    @PUT("api/habits/{id}")
    suspend fun updateHabit(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: CreateHabitRequest
    ): Response<ApiHabit>

    @DELETE("api/habits/{id}")
    suspend fun deleteHabit(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<GenericResponse>

    // Returns a plain JSON array
    @GET("api/goals")
    suspend fun getGoals(@Header("Authorization") token: String): Response<List<ApiGoal>>

    @POST("api/goals")
    suspend fun createGoal(
        @Header("Authorization") token: String,
        @Body request: CreateGoalRequest
    ): Response<ApiGoal>

    @PUT("api/goals/{id}")
    suspend fun updateGoal(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body request: CreateGoalRequest
    ): Response<ApiGoal>

    @DELETE("api/goals/{id}")
    suspend fun deleteGoal(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<GenericResponse>

    // Returns the stats object directly
    @GET("api/statistics")
    suspend fun getStatistics(@Header("Authorization") token: String): Response<ApiStatistics>

    // Returns a plain JSON array
    @GET("api/achievements")
    suspend fun getAchievements(@Header("Authorization") token: String): Response<List<ApiAchievement>>

    @POST("api/habit-completions")
    suspend fun logHabitCompletion(
        @Header("Authorization") token: String,
        @Body request: HabitCompletionRequest
    ): Response<HabitCompletionResponse>

    @DELETE("api/habit-completions/{habitId}/today/last")
    suspend fun removeHabitCompletion(
        @Header("Authorization") token: String,
        @Path("habitId") habitId: Int,
        @Query("amount") amount: Int = 1
    ): Response<HabitCompletionResponse>
}
