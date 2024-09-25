package com.example.postily.network

import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface FeedApiService {

    // Fetch all posts
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    // Fetch comments for a post
    @GET("/comments")
    suspend fun getComments(@Query("postId") postId: Int): List<Comment>
}