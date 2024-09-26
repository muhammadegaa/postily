package com.example.postily.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import com.example.postily.repository.FeedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

<<<<<<< HEAD
class FeedViewModel : ViewModel() {
    private val repository = FeedRepository()
=======
@HiltViewModel
class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
): ViewModel() {
>>>>>>> 42dbe2a6bc5a78b4ab0b1ed737ed48a49c6ad859

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