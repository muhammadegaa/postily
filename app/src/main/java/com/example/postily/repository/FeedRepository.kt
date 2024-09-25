package com.example.postily.repository

import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import com.example.postily.network.FeedApiService
import com.example.postily.network.RetrofitInstance

class FeedRepository {
    private val apiService: FeedApiService = RetrofitInstance.createService(FeedApiService::class.java)

    suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }

    suspend fun getComments(postId: Int): List<Comment> {
        return apiService.getComments(postId)
    }
}