package com.example.postily.view.profile

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.model.profile.User
import com.example.postily.view.profile.friend.FriendItem
import com.example.postily.viewmodel.AuthViewModel
import com.example.postily.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val user by userViewModel.user.collectAsState()
    val friends by userViewModel.friends.collectAsState()
    val context = LocalContext.current
    val firebaseUser = FirebaseAuth.getInstance().currentUser

    Column(modifier = Modifier.padding(16.dp)) {
        user?.let {
            // Profile picture and name
            AsyncImage(
                model = "https://picsum.photos/200",
                contentDescription = "Profile picture",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Row for user name and logout button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = firebaseUser?.displayName ?: "Unknown User",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )

                // Logout button at the right end
                Button(
                    onClick = {
                        authViewModel.logout()
                        navController.navigate("auth") {
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                ) {
                    Text("Logout")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            // Address clickable to open in Maps
            Row(modifier = Modifier.clickable {
                val geoUri = Uri.parse("geo:0,0?q=${it.address.street}, ${it.address.city}")
                val intent = Intent(Intent.ACTION_VIEW, geoUri)
                context.startActivity(intent)
            }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Location")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${it.address.street}, ${it.address.city}")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Friends list
            Text(text = "Friends List", style = MaterialTheme.typography.bodyLarge)

            LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
                items(friends) { friend ->
                    FriendItem(friend) {
                        navController.navigate("friendDetail/${friend.id}")
                    }
                }
            }
        } ?: run {
            Text(text = "Loading profile...")
        }
    }
}