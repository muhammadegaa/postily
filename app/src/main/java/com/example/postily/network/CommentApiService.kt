package com.example.postily.network

import com.example.postily.model.feed.Comment
import retrofit2.http.GET

interface CommentApiService {
    @GET("comments")
    suspend fun getComments(): List<Comment>
}