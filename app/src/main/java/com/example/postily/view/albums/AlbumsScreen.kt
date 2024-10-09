package com.example.postily.view.albums

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.model.albums.Album
import com.example.postily.viewmodel.AlbumViewModel

@Composable
fun AlbumsScreen(navController: NavController, viewModel: AlbumViewModel = hiltViewModel()) {
    val albums by viewModel.albums.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(albums) { album ->
            AlbumListItem(album) {
                navController.navigate("albumDetail/${album.id}")
            }
        }
    }
}

@Composable
fun AlbumListItem(album: Album, onClick: () -> Unit) {
    val shape =  RoundedCornerShape(8.dp)
    val height = 200.dp
    Box(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .clip(shape = RoundedCornerShape(40.dp))
            .padding(6.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = "https://picsum.photos/200",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(shape)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                color = Color.Black.copy(alpha = 0.3f),
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = album.title,
                    color = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}