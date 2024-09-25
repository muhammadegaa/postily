package com.example.postily.repository

import com.example.postily.model.tasks.Task
import com.example.postily.network.RetrofitInstance
import com.example.postily.network.TaskApiService

class TaskRepository {
    private val apiService: TaskApiService = RetrofitInstance.createService(TaskApiService::class.java)

    // Fetch all tasks from the API
    suspend fun getTasks(): List<Task> {
      