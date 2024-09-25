package com.example.postily.repository

import com.example.postily.model.albums.Album
import com.example.postily.model.albums.Photo
import com.example.postily.network.AlbumApiService
import com.example.postily.network.RetrofitInstance

class AlbumRepository {
    private val apiService: AlbumApiService = RetrofitInstance.createService(AlbumApiService::class.java)

    // Fetch all albums from the API
    suspend fun getAlbums(): List<Album> {
        return apiService.getAlbums()
    }

    // Fetch photos for a specific album
    suspend fun getPhotos(albumId: Int): List<Photo> {
        return apiService.getPhotos(albumId)
    }

    // Fetch a specific photo by its ID
    suspend fun getPhoto(photoId: Int): Photo {
        return apiService.getPhoto(photoId)
    }
}