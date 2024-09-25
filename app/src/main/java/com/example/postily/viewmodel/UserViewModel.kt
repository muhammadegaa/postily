package com.example.postily.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.profile.User
import com.example.postily.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val repository = UserRepository()

    var user = mutableStateOf<User?>(null)
        private set

    var friends = mutableStateListOf<User>()
        private set

    init {
        fetchUser()
        fetchFriends()
    }

    // Fetch user profile
    private fun fetchUser() {
        viewModelScope.launch {
            val fetchedUser = repository.getUser()
            user.value = fetchedUser
        }
    }

    // Fetch user's friends
    private fun fetchFriends() {
        viewModelScope.launch {
            val fetchedFriends = repository.getFriends()
            friends.addAll(fetchedFriends)
        }
    }

    // Get a specific friend by ID (for detailed screens)
    fun getFriendById(friendId: Int): User? {
        return friends.find { it.id == friendId }
    }
}