package com.example.postily.network

import com.example.postily.model.profile.User
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApiService {

    // Fetch user profile by ID
    @GET("/users/{userId}")
    suspend fun getUser(@Path("userId") userId: Int): User

    // Fetch all friends (this is just for the sake of example)
    @GET("/users")
    suspend fun getFriends(): List<User>
}