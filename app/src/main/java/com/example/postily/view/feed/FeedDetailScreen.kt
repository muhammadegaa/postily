package com.example.postily.view.feed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.postily.model.feed.Comment
import com.example.postily.viewmodel.FeedViewModel

@Composable
fun FeedDetailScreen(
    navController: NavController,
    postId: String?,
    viewModel: FeedViewModel = viewModel()  // ViewModel for managing posts and comments
) {
    val post = viewModel.getPostById(postId?.toInt() ?: 0)
    val comments by remember { mutableStateOf(viewModel.comments) }
    var isLoading by remember { mutableStateOf(comments.isEmpty()) }

    // Fetch comments for the selected post
    if (post != null) {
        LaunchedEffect(post.id) {
            viewModel.fetchComments(post.id)
            isLoading = false
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = post?.title ?: "No Title", style = androidx.compose.material3.MaterialTheme.typography.headlineSmall)
        Text(text = post?.body ?: "No Content", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Comments:", modifier = Modifier.padding(top = 16.dp))

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
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