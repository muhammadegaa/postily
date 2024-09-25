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

@Composable
fun FeedScreen(navController: NavHostController, viewModel: FeedViewModel = viewModel()) {
    val posts = viewModel.posts

    LazyColumn {
        items(posts) { post ->
            PostItem(post) {
                // Navigate to Feed Detail Screen