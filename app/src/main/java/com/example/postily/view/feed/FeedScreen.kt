package com.example.postily.view.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.postily.model.feed.Post
import com.example.postily.viewmodel.FeedViewModel

@Composable
fun FeedScreen(navController: NavHostController, viewModel: FeedViewModel = viewModel()) {
    val posts = viewModel.posts

    LazyColumn {
        items(posts) { post ->
            PostItem(post) {
                // Navigate to Feed Detail Screen when a post is clicked
                navController.navigate("feedDetail/${post.id}")
            }
        }
    }
}

@Composable
fun PostItem(post: Post, onClick: () -> Unit) {
    Card(modifier = Modifier.padding(8.dp).clickable { onClick() }) {
        Text(text = post.title)
        Text(text = post.body)
    }
}