package com.example.postily.view.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.postily.model.profile.User

@Composable
fun FriendItem(friend: User, onClick: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable(onClick = onClick)) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = "Friend: ${friend.name}")
            Text(text = "Username: ${friend.username}")
        }
    }
}
