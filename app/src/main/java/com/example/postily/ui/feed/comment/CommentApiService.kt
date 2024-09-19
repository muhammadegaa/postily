package com.example.postily.ui.feed.comment

import retrofit2.http.GET

interface CommentApiService {
    @GET("comments")
    suspend fun getComments(): List<Comment>
}