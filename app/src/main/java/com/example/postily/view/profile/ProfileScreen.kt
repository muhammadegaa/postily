package com.example.postily.view.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.view.profile.friend.FriendItem
import com.example.postily.viewmodel.AuthViewModel
import com.example.postily.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel() // Inject AuthViewModel for logout
) {
    val user by userViewModel.user.collectAsState()
    val friends by userViewModel.friends.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        user?.let {
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
                horizontalArrangement = Arrangement.SpaceBetween // Ensures logout is at the end
            ) {
                // Display user details
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.weight(1f)
                )

                // Logout button at the right end
                Button(
                    onClick = {
                        authViewModel.logout() // Call logout from AuthViewModel
                        navController.navigate("auth") { // Navigate back to auth screen
                            popUpTo("profile") { inclusive = true }
                        }
                    }
                ) {
                    Text("Logout")
                }
            }
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