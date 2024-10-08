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

    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    private val _comments = MutableStateFlow<List<Comment>>(emptyList())
    val comments: StateFlow<List<Comment>> = _comments

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchPosts()
    }

    // Fetch posts from Firestore
    fun fetchPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            feedRepository.getPosts().let { result ->
                _posts.value = result
                _isLoading.value = false
            }
        }
    }

    // Fetch comments for a specific post from Firestore
    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            _comments.value = feedRepository.getComments(postId)
        }
    }
}
