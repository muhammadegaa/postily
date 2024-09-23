package com.example.postily.model.feed

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)