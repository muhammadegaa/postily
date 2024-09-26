package com.example.postily.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.albums.Album
import com.example.postily.model.albums.Photo
import com.example.postily.network.AlbumApiService
import com.example.postily.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AlbumViewModel : ViewModel() {

    // Use Retrofit to get the AlbumApiService
    private val albumApiService: AlbumApiService = RetrofitInstance.createService(AlbumApiService::class.java)

    // StateFlows to manage the state of albums and photos
    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos

    // Fetch albums when the ViewModel is initialized
    init {
        fetchAlbums()
    }

    // Function to fetch albums from the API
    fun fetchAlbums() {
        viewModelScope.launch {
            try {
                val albumList = albumApiService.getAlbums()
                _albums.value = albumList
            } catch (e: Exception) {
                // Handle any errors here (e.g., log the error)
            }
        }
    }

    // Function to fetch photos of a specific album from the API
    fun fetchPhotos(albumId: Int) {
        viewModelScope.launch {
            try {
                val photoList = albumApiService.getPhotos(albumId)
                _photos.value = photoList
            } catch (e: Exception) {
                // Handle any errors here (e.g., log the error)
            }
        }
    }

    // Function to retrieve a specific album by ID
    fun getAlbumById(albumId: Int): Album? {
        return _albums.value.find { it.id == albumId }
    }

    // Function to retrieve a specific photo by ID
    fun getPhotoById(photoId: Int): Photo? {
        return _photos.value.find { it.id == photoId }
    }
}
