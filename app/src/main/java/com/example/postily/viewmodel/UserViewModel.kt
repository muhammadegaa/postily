package com.example.postily.viewmodel

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
        fetchUser()
        fetchFriends()
    }

    private fun fetchUser() {
        viewModelScope.launch {
            val fetchedUser = userRepository.getUser()
            _user.value = fetchedUser
        }
    }

    private fun fetchFriends() {
        viewModelScope.launch {
            val fetchedFriends = userRepository.getFriends()
            _friends.value = fetchedFriends
        }
    }

    fun getFriendById(friendId: Int): User? {
        return _friends.value.find { it.id == friendId }
    }
}