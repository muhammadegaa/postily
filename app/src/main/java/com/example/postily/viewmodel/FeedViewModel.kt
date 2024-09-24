package com.example.postily.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.CommentResponse
import com.example.postily.model.feed.PostResponse
import com.example.postily.repository.FeedRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val repository: FeedRepository
): ViewModel() {

    private val _posts = MutableLiveData<List<PostResponse>>()
    val posts: LiveData<List<PostResponse>> get() = _posts

    private val _comments = MutableLiveData<List<CommentResponse>>()
    val comments: LiveData<List<CommentResponse>> get() = _comments

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun fetchPosts(){
        viewModelScope.launch {
            try {
                val postList = repository.getPosts()
                _posts.postValue(postList)
            } catch (e: Exception){
                _errorMessage.postValue("Failed to load posts")
            }
        }
    }

    fun fetchComments(postId: Int){
        viewModelScope.launch {
            try {
                val commentList = repository.getComments(postId)
                _comments.postValue(commentList)
            } catch (e: Exception) {
                _errorMessage.postValue("Failed to load comments")
            }
        }
    }
}