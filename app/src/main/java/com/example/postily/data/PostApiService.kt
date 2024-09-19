package com.example.postily.data

import com.example.postily.ui.feed.Feed
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<Feed>
}