package com.example.postily.model.tasks

data class Task(
    val id: Int,
    val userId: Int,
    val title: String,
    val completed: Boolean
)