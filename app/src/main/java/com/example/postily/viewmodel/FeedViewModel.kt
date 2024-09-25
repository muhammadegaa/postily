package com.example.postily.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import com.example.postily.repository.FeedRepository
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {
    private val repository = FeedRepository()

    var posts = mutableStateListOf<Post>()
        private set

    var comments = mutableStateListOf<Comment>()
        private set

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            val fetchedPosts = repository.getPosts()
            posts.addAll(fetchedPosts)
        }
    }

    fun fetchComments(postId: Int) {
        viewModelScope.launch {
            val fetchedComments = repository.getComments(postId)
            comments.clear()
            comments.addAll(fetchedComments)
        }
    }

    fun getPostById(postId: Int): Post? {
        return posts.find { it.id == postId }
    }
}