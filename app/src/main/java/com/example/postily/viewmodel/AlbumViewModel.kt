package com.example.postily.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.albums.Album
import com.example.postily.model.albums.Photo
import com.example.postily.repository.AlbumRepository
import kotlinx.coroutines.launch

class AlbumViewModel : ViewModel() {
    private val repository = AlbumRepository()

    var albums = mutableStateListOf<Album>()
        private set

    var photos = mutableStateListOf<Photo>()
        private set

    init {
        fetchAlbums()
    }

    // Fetch the list of albums from the repository
    private fun fetchAlbums() {
        viewModelScope.launch {
            val fetchedAlbums = repository.getAlbums()
            albums.addAll(fetchedAlbums)
        }
    }

    // Fetch the photos of a specific album
    fun fetchPhotos(albumId: Int) {
        viewModelScope.launch {
            val fetchedPhotos = repository.getPhotos(albumId)
            photos.clear()
            photos.addAll(fetchedPhotos)
        }
    }

    // Get a specific photo by ID
    fun getPhotoById(photoId: Int): Photo? {
        return photos.find { it.id == photoId }
    }
}