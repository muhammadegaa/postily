package com.example.postily.repository

import com.example.postily.model.profile.User
import com.example.postily.network.RetrofitInstance
import com.example.postily.network.UserApiService
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: UserApiService
) {
    // Fetch the current user's profile
    suspend fun getUser(): User {
        return apiService.getUser(1)  // Assuming user ID 1 for simplicity
    }

    // Fetch all friends of the user
    suspend fun getFriends(): List<User> {
        return apiService.getFriends()
    }
}
