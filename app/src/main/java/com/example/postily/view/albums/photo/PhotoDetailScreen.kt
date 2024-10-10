package com.example.postily.view.albums.photo

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
    photoUrl: String?,
    photoTitle: String?
) {
    val context = LocalContext.current

    // Decode the photoUrl to get the actual URL
    val decodedUrl = Uri.decode(photoUrl)

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = photoTitle ?: "No Title", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        // Display the full-size image
        AsyncImage(
            model = decodedUrl,  // Use the decoded URL
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Share Button
        Button(onClick = {
            // Share the photo
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, decodedUrl ?: "")
            }
            val shareIntent = Intent.createChooser(intent, null)
            context.startActivity(shareIntent)
        }) {
            Text("Share Photo")
        }
    }
}