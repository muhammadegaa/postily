package com.example.postily.network

import com.example.postily.model.tasks.Task
import retrofit2.http.GET

interface TodoApi {
    @GET("todos")
    suspend fun getTodos(): List<Task>
}