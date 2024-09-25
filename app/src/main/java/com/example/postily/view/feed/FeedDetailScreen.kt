package com.example.postily.view.feed

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavController
import com.example.postily.model.feed.Comment

@Composable
fun FeedDetailScreen(
    navController: NavController,
    postId: String?,
    viewModel: FeedViewModel = viewModel()
) {
    val post = viewModel.getPostById(postId?.toInt() ?: 0)

    // Fetch comments for the selected post
    if (post != null) {
        viewModel.fetchComments(post.id)
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = post?.title ?: "No Title", style = MaterialTheme.typography.headlineSmall)
        Text(text = post?.body ?: "No Content", style = MaterialTheme.typography.bodyLarge)

        Text(text = "Comments:", modifier = Modifier.padding(top = 16.dp))

        LazyColumn {
            items(viewModel.comments) { comment ->
                CommentItem(comment)
            }
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = commen