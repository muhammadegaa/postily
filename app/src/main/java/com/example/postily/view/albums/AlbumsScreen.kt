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
import com.example.postily.model.albums.Album
import com.example.postily.viewmodel.AlbumViewModel

@Composable
fun AlbumsScreen(navController: NavController, viewModel: AlbumViewModel = viewModel()) {
    val albums = viewModel.albums

    LazyColumn {
        items(albums) { album ->
            AlbumItem(album) {
                // Navigate to Album Detail Screen
                navController.navigate("albumDetail/${album.id}")
            }
        }
    }
}

@Composable
fun AlbumItem(album: Album, onClick: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable(onClick = onClick)) {
        Text(text = album.title)
    }
}