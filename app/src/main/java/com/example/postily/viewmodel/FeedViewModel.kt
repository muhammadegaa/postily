package com.example.postily.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import com.example.postily.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val feedRepository: FeedRepository
) : ViewModel() {

    // MutableStateFlow to hold posts
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    // MutableStateFlow to hold comments
    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    // MutableStateFlow to track loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Initialization block to fetch posts as soon as the ViewModel is created
    init {
        fetchPosts()
    }

    // Function to fetch posts
    fun fetchPosts() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val posts = feedRepository.getPosts()
                _posts.value = posts
            } catch (e: Exception) {
                // Handle error
                _posts.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Function to fetch comments for a specific post
    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val comments = feedRepository.getComments(postId)
                _comments.value = comments
            } catch (e: Exception) {
                // Handle error
                _comments.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
