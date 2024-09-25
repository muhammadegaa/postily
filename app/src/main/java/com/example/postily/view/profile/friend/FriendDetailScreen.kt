package com.example.postily.view.profile.friend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postily.viewmodel.UserViewModel

@Composable
fun FriendDetailScreen(navController: NavController, friendId: String?, viewModel: UserViewModel = viewModel()) {
    val friend = viewModel.getFriendById(friendId?.toInt() ?: 0)

    Column(modifier = Modifier.padding(16.dp)) {
        if (friend != null) {
            Text(text = "Friend's Name: ${friend.name}")
            Text(text = "Phone: ${friend.phone}")
            Text(text = "Email: ${friend.email}")
            Text(text = "Company: ${friend.company.name}")
        } else {
            Text(text = "Friend not found.")
        }
    }
}
