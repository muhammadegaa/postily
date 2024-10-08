package com.example.postily.repository

import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import com.example.postily.network.FeedApiService
import com.example.postily.network.RetrofitInstance
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FeedRepository @Inject constructor(
    private val apiService: FeedApiService,
    private val firestore: FirebaseFirestore
) {

    // Function to get posts from API
    suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }

    // Function to get comments from API
    suspend fun getComments(postId: Int): List<Comment> {
        return apiService.getComments(postId)
    }

    // Function to save posts to Firestore
    suspend fun savePostsToFirestore(posts: List<Post>) {
        try {
            posts.forEach { post ->
                firestore.collection("posts")
                    .document(post.id.toString())
                    .set(post)
                    .await()
            }
        } catch (e: Exception) {
            // Handle error when saving to Firestore
            e.printStackTrace()
        }
    }

    suspend fun getPostsFromFirestore(): List<Post> {
        return try {
            val snapshot = firestore.collection("posts").get().await()
            snapshot.documents.mapNotNull { it.toObject(Post::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
}