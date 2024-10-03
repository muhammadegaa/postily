package com.example.postily.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.postily.model.albums.Album
import com.example.postily.model.albums.Photo
import com.example.postily.repository.AlbumRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumViewModel @Inject constructor(
    private val repository: AlbumRepository
) : ViewModel() {

    private val _albums = MutableStateFlow<List<Album>>(emptyList())
    val albums: StateFlow<List<Album>> = _albums

    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    val photos: StateFlow<List<Photo>> = _photos

    init {
        fetchAlbums()  // Fetch albums when ViewModel is created
    }

    // Fetch all albums from the repository
    private fun fetchAlbums() {
        viewModelScope.launch {
            try {
                val albumList = repository.getAlbums()
                _albums.value = albumList
            } catch (e: Exception) {
                e.message?.let { Log.e("AlbumViewModel", it) }
            }
        }
    }

    // Fetch photos for a specific album by ID
    fun fetchPhotos(albumId: Int) {
        viewModelScope.launch {
            try {
                val photoList = repository.getPhotos(albumId)
                _photos.value = photoList
            } catch (e: Exception) {
                // Handle any errors here (e.g., log the error)
            }
        }
    }

    // Retrieve a specific album by its ID
    fun getAlbumById(albumId: Int): Album? {
        return _albums.value.find { it.id == albumId }
    }

    // Retrieve a specific photo by its ID
    fun getPhotoById(photoId: Int): Photo? {
        return _photos.value.find { it.id == photoId }
    }
}