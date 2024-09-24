package com.example.postily.model.feed

data class PostResponse (
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)