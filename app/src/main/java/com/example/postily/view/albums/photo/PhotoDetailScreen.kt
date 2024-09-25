package com.example.postily.view.albums.photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel  // Ensure correct import for viewModel() function
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.viewmodel.AlbumViewModel

@Composable
fun PhotoDetailScreen(navController: NavController, photoId: String?) {
    // Use viewModel() to retrieve the instance of AlbumViewModel
    val viewModel: AlbumViewModel = viewModel()

    // Retrieve the photo by its ID
    val photo = viewModel.getPhotoById(photoId?.toInt() ?: 0)

    // UI layout for displaying photo details
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = photo?.title ?: "No Title", style = MaterialTheme.typography.headlineSmall)

        AsyncImage(
            model = photo?.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )
    }
}