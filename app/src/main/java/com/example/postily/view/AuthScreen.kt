package com.example.postily.view

import android.content.Intent
import android.graphics.fonts.FontFamily
import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.Insets.add
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.example.postily.viewmodel.AuthViewModel
import com.example.postily.R

@Composable
fun AuthScreen(
    onGoogleSignIn: (Intent) -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val authHeader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("https://picsum.photos/300/200")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.sample_image),
                contentDescription = stringResource(R.string.auth_header_desc),
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(CircleShape)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Welcome to Postily", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    Toast.makeText(context, "Sign in clicked!", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Login")
            }
            Button(
                onClick = {
                    Toast.makeText(context, "Sign up clicked!", Toast.LENGTH_SHORT).show()
                }
            ) {
                Text("Register")
            }
        }
    }
}
