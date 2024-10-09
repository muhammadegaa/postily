package com.example.postily.view.albums.photo

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.viewmodel.AlbumViewModel

@Composable
fun PhotoDetailScreen(
    navController: NavController,
    photoId: String?,
    viewModel: AlbumViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val photo = viewModel.getPhotoById(photoId?.toInt() ?: 0)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = photo?.title ?: "No Title", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = photo?.url,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Share the photo
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"  // For sharing text or image
                putExtra(Intent.EXTRA_TEXT, photo?.url ?: "")
            }
            val shareIntent = Intent.createChooser(intent, null)
            context.startActivity(shareIntent)
        }) {
            Text("Share Photo")
        }
    }
}