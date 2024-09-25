package com.example.postily.network

import com.example.postily.model.albums.Album
import com.example.postily.model.albums.Photo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AlbumApiService {

    @GET("/albums")
    suspend fun getAlbums(): List<Album>

    @GET("/photos")
    suspend fun getPhotos(@Query("albumId") albumId: Int): List<Photo>

    @GET("/photos/{photoId}")
    suspend fun getPhoto(@Path("photoId") photoId: Int): Photo
}