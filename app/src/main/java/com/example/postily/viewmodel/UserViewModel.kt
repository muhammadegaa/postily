package com.example.postily.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.profile.User
import com.example.postily.network.UserApiService
import com.example.postily.repository.UserRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _friends = MutableStateFlow<List<User>>(emptyList())
    val friends: StateFlow<List<User>> = _friends

    init {
        fetchUser(1)  // Assuming user ID 1 for this example
        fetchFriends()
    }

    private fun fetchUser(userId: Int) {
        viewModelScope.launch {
            _user.value = userRepository.getUser(userId)
        }
    }

    private fun fetchFriends() {
        viewModelScope.launch {
            _friends.value = userRepository.getFriends()
        }
    }

    fun getFriendById(friendId: Int): User? {
        return _friends.value.find { it.id == friendId }
    }
}