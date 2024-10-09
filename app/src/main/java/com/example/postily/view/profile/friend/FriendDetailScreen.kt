package com.example.postily.view.profile.friend

import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.viewmodel.UserViewModel

@Composable
fun FriendDetailScreen(
    navController: NavController,
    friendId: String?,
    viewModel: UserViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val friend = viewModel.getFriendById(friendId?.toInt() ?: 0)

    Column(modifier = Modifier.padding(16.dp)) {
        if (friend != null) {
            AsyncImage(
                model = "https://picsum.photos/200",
                contentDescription = "Friend profile picture",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = friend.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Phone: ${friend.phone}")
            Text(text = "Email: ${friend.email}")
            Text(text = "Company: ${friend.company.name}")

            Spacer(modifier = Modifier.height(16.dp))

            // Email Button
            Button(onClick = {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${friend.email}")
                }
                context.startActivity(intent)
            }) {
                Text("Send Email")
            }

            // Phone Button
            Button(onClick = {
                val intent = Intent(Intent.ACTION_DIAL).apply {
                    data = Uri.parse("tel:${friend.phone}")
                }
                context.startActivity(intent)
            }) {
                Text("Call")
            }

            // Address Button
            Button(onClick = {
                val geoUri = Uri.parse("geo:0,0?q=${friend.address.street}, ${friend.address.city}")
                val intent = Intent(Intent.ACTION_VIEW, geoUri)
                context.startActivity(intent)
            }) {
                Text("View Address on Map")
            }
        } else {
            Text(text = "Friend not found.")
        }
    }
}