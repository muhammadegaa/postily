package com.example.postily.model.tasks

data class Task(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)