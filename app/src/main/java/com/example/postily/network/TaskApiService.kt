package com.example.postily.network

import com.example.postily.model.tasks.Task
import retrofit2.http.GET

interface TaskApiService {

    // Fetch all tasks
    @GET("/todos")
    suspend fun getTasks(): List<Task>
}