package com.example.postily.view.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.postily.view.profile.friend.FriendItem
import com.example.postily.viewmodel.UserViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: UserViewModel = hiltViewModel()) {
    val user by viewModel.user.collectAsState()
    val friends by viewModel.friends.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        // User's Profile Information
        user?.let {
            // Display profile image
            AsyncImage(
                model = "https://picsum.photos/200",
                contentDescription = "Profile picture",
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Display user details
            Text(text = it.name, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Name and person details", style = MaterialTheme.typography.bodyMedium)
        } ?: run {
            Text(text = "Loading profile...")
        }

        // Friends List
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Friends List", style = MaterialTheme.typography.bodyLarge)

        LazyColumn(modifier = Modifier.padding(top = 8.dp)) {
            items(friends) { friend ->
                FriendItem(friend) {
                    // Navigate to FriendDetailScreen when a friend is clicked
                    navController.navigate("friendDetail/${friend.id}")
                }
            }
        }
    }
}