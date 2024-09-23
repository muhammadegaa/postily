package com.example.postily.network

import com.example.postily.model.feed.Feed
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<Feed>
}