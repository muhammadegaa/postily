package com.example.postily.view.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.model.albums.Photo
import com.example.postily.viewmodel.AlbumViewModel

@Composable
fun AlbumDetailScreen(navController: NavController, albumId: String?, viewModel: AlbumViewModel = hiltViewModel()) {
    // Fetch the photos of the selected album
    albumId?.let { viewModel.fetchPhotos(it.toInt()) }

    val photos by viewModel.photos.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
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
                model = photo.thumbnailUrl,  // Using thumbnailUrl from API
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            )
//            Spacer(modifier = Modifier.height(8.dp))
//            Text(text = photo.title, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
