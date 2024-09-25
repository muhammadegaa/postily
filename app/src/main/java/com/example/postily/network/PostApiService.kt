package com.example.postily.network

import com.example.postily.model.albums.Album
import com.example.postily.model.albums.Photo
import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import com.example.postily.model.profile.User
import com.example.postily.model.tasks.Task
import retrofit2.http.GET
import retrofit2.http.Path

interface PostApiService {
    @GET("/posts")
    suspend fun getPosts(): List<Post>

    @GET("/comments?postId={postId}")
    suspend fun getComments(@Path("postId") postId: Int): List<Comment>

    @GET("/albums")
    suspend fun getAlbums(): List<Album>

    @GET("/photos?albumId={albumId}")
    suspend fun getPhotos(@Path("albumId") albumId: Int): List<Photo>

    @GET("/todos")
    suspend fun getTasks(): List<Task>

    @GET("/users")
    suspend fun getUsers(): List<User>
}