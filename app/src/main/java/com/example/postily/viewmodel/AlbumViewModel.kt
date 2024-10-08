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
        fetchAlbums()
    }

    // Fetch all albums from Firestore first, fallback to API if Firestore is empty
    private fun fetchAlbums() {
        viewModelScope.launch {
            val albumsFromFirestore = repository.getAlbumsFromFirestore()
            if (albumsFromFirestore.isNotEmpty()) {
                _albums.value = albumsFromFirestore
            } else {
                // If Firestore is empty, fetch from API and store to Firestore
                val albumsFromApi = repository.getAlbums()
                repository.saveAlbumsToFirestore(albumsFromApi)
                _albums.value = albumsFromApi
            }
        }
    }

    // Fetch photos for a specific album
    fun fetchPhotos(albumId: Int) {
        viewModelScope.launch {
            val photosFromFirestore = repository.getPhotosFromFirestore(albumId)
            if (photosFromFirestore.isNotEmpty()) {
                _photos.value = photosFromFirestore
            } else {
                // If Firestore is empty, fetch from API and store to Firestore
                val photosFromApi = repository.getPhotos(albumId)
                repository.savePhotosToFirestore(albumId, photosFromApi)
                _photos.value = photosFromApi
            }
        }
    }

    // Retrieve a specific photo by its ID
    fun getPhotoById(photoId: Int): Photo? {
        return _photos.value.find { it.id == photoId }
    }
}