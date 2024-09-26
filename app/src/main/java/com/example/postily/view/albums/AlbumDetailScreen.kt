package com.example.postily.view.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.model.albums.Photo
import com.example.postily.viewmodel.AlbumViewModel

@Composable
fun AlbumDetailScreen(navController: NavController, albumId: String?, viewModel: AlbumViewModel = viewModel()) {
    // Fetch the photos of the selected album
    albumId?.let { viewModel.fetchPhotos(it.toInt()) }

    val photos by viewModel.photos.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(photos) { photo ->
            PhotoItem(photo) {
                navController.navigate("photoDetail/${photo.id}")
            }
        }
    }
}

@Composable
fun PhotoItem(photo: Photo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = photo.thumbnailUrl,
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = photo.title, style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
        }
    }
}
