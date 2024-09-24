package com.example.postily.network

import com.example.postily.model.feed.CommentResponse
import com.example.postily.model.feed.PostResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<PostResponse>

    @GET("comments")
    suspend fun getComments(@Query("postId") postId: Int): List<CommentResponse>
}