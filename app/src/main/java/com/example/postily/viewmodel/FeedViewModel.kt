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
            _isLoading.value = true
            try {
                // Fetch posts from the repository
                val result = feedRepository.getPosts()
                // Update the posts state with the fetched data
                _posts.value = result
            } catch (e: Exception) {
                // If an error occurs, set posts to an empty list
                _posts.value = emptyList()
            } finally {
                _isLoading.value = false // Mark loading as done
            }
        }
    }

    // Function to fetch comments for a specific post
    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Fetch comments from the repository
                val result = feedRepository.getComments(postId)
                // Update the comments state with the fetched data
                _comments.value = result
            } catch (e: Exception) {
                // If an error occurs, set comments to an empty list
                _comments.value = emptyList()
            } finally {
                _isLoading.value = false // Mark loading as done
            }
        }
    }
}
