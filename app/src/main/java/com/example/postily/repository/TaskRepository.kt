package com.example.postily.repository

import com.example.postily.model.tasks.Task
import com.example.postily.network.RetrofitInstance
import com.example.postily.network.TaskApiService
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val apiService: TaskApiService
) {
    // Fetch all tasks from the API
    suspend fun getTasks(): List<Task> {
        return apiService.getTasks()
    }
}