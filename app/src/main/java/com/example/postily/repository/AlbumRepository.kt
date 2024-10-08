package com.example.postily.repository

import com.example.postily.model.albums.Album
import com.example.postily.model.albums.Photo
import com.example.postily.network.AlbumApiService
import com.example.postily.network.RetrofitInstance
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AlbumRepository @Inject constructor(
    private val apiService: AlbumApiService,
    private val firestore: FirebaseFirestore
) {

    // Fetch all albums from the API
    suspend fun getAlbums(): List<Album> {
        return apiService.getAlbums()
    }

    // Fetch all albums from Firestore
    suspend fun getAlbumsFromFirestore(): List<Album> {
        return try {
            val snapshot = firestore.collection("albums").get().await()
            snapshot.documents.mapNotNull { it.toObject(Album::class.java) }
        } catch (e: Exception) {
            emptyList()  // Return an empty list in case of an error
        }
    }

    // Save albums to Firestore
    suspend fun saveAlbumsToFirestore(albums: List<Album>) {
        try {
            albums.forEach { album ->
                firestore.collection("albums")
                    .document(album.id.toString())
                    .set(album)
                    .await()
            }
        } catch (e: Exception) {
            e.printStackTrace()  // Log error in case of failure
        }
    }

    // Fetch photos for a specific album
    suspend fun getPhotos(albumId: Int): List<Photo> {
        return apiService.getPhotos(albumId)
    }

    // Fetch photos from Firestore for a specific album
    suspend fun getPhotosFromFirestore(albumId: Int): List<Photo> {
        return try {
            val snapshot = firestore.collection("albums")
                .document(albumId.toString())
                .collection("photos")
                .get()
                .await()
            snapshot.documents.mapNotNull { it.toObject(Photo::class.java) }
        } catch (e: Exception) {
            emptyList()  // Return an empty list in case of an error
        }
    }

    // Save photos to Firestore
    suspend fun savePhotosToFirestore(albumId: Int, photos: List<Photo>) {
        try {
            photos.forEach { photo ->
                firestore.collection("albums")
                    .document(albumId.toString())
                    .collection("photos")
                    .document(photo.id.toString())
                    .set(photo)
                    .await()
            }
        } catch (e: Exception) {
            e.printStackTrace()  // Log error in case of failure
        }
    }
}