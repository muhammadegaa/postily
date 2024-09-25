package com.example.postily.view.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postily.model.albums.Photo
import com.example.postily.viewmodel.AlbumViewModel

@Composable
fun AlbumDetailScreen(navController: NavController, albumId: String?, viewModel: AlbumViewModel = viewModel()) {
    // Fetch the photos of the selected album
    albumId?.let { viewModel.fetchPhotos(it.toInt()) }

    val photos = viewModel.photos

    LazyColumn {
        items(photos) { photo ->
            PhotoItem(photo) {
                // Navigate to PhotoDetailScreen when a photo is clicked
                navController.navigate("photoDetail/${photo.id}")
            }
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onClick: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable(onClick = onClick)) {
        Text(text = photo.title)
    }
}