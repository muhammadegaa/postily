package com.example.postily.view.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postily.model.albums.Album
import com.example.postily.viewmodel.AlbumViewModel

@Composable
fun AlbumsScreen(navController: NavController, viewModel: AlbumViewModel = viewModel()) {
    val albums by viewModel.albums.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(albums) { album ->
            AlbumListItem(album) {
                navController.navigate("albumDetail/${album.id}")
            }
        }
    }
}

@Composable
fun AlbumListItem(album: Album, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = album.title, style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
        }
    }
}
