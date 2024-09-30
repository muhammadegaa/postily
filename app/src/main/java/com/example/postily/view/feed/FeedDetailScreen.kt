package com.example.postily.view.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postily.model.feed.Comment
import com.example.postily.viewmodel.FeedViewModel

@Composable
fun FeedDetailScreen(
    navController: NavController,
    postId: String?,
    viewModel: FeedViewModel = hiltViewModel()
) {
    val post = viewModel.getPostById(postId?.toInt() ?: 0)
    val comments by viewModel.comments.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Fetch comments for the selected post
    if (post != null) {
        LaunchedEffect(post.id) {
            viewModel.fetchComments(post.id)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = post?.title ?: "No Title",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall
        )
        Text(
            text = post?.body ?: "No Content",
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Comments:")

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                items(comments) { comment ->
                    CommentItem(comment)
                }
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = comment.name, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        Text(text = comment.body, style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
    }
}