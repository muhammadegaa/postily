package com.example.postily.view.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postily.viewmodel.UserViewModel

@Composable
fun ProfileScreen(navController: NavController, viewModel: UserViewModel = viewModel()) {
    val user = viewModel.user.value
    val friends = viewModel.friends

    Column(modifier = Modifier.padding(16.dp)) {
        // User's Profile Information
        if (user != null) {
            Text(text = "Name: ${user.name}")
            Text(text = "Username: ${user.username}")
            Text(text = "Email: ${user.email}")
            Text(text = "Phone: ${user.phone}")
            Text(text = "Company: ${user.company.name}")
            Text(text = "Website: ${user.website}")
        } else {
            Text(text = "Loading profile...")
        }

        // Friends List
        Text(text = "Friends", modifier = Modifier.padding(top = 16.dp))
        LazyColumn {
            items(friends) { friend ->
                FriendItem(friend) {
                    // Navigate to FriendDetailScreen when a friend is clicked
                    navController.navigate("friendDetail/${friend.id}")
                }
            }
        }
    }
}
