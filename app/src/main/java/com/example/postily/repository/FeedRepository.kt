package com.example.postily.repository

import android.util.Log
import com.example.postily.model.feed.CommentResponse
import com.example.postily.model.feed.FeedEntity
import com.example.postily.model.feed.PostResponse
import com.example.postily.network.FeedApiService
import com.example.postily.network.PostApiService
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val apiService: PostApiService,
) {

    suspend fun getPosts(): List<PostResponse> {
        return apiService.getPosts()
    }

    suspend fun getComments(postId: Int): List<CommentResponse>{
        return apiService.getComments(postId)
    }
}