package com.example.postily.view.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.postily.model.feed.Comment
import com.example.postily.model.feed.Post
import com.example.postily.viewmodel.FeedViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(navController: NavController, viewModel: FeedViewModel = hiltViewModel()) {
    val posts by viewModel.posts.collectAsState() // Collect posts as state
    val isLoading by viewModel.isLoading.collectAsState() // Collect loading state
    val sheetState = rememberModalBottomSheetState()
    val coroutineScope = rememberCoroutineScope()
    var selectedPost by remember { mutableStateOf<Post?>(null) } // Mutable state to track selected post
    val comments by viewModel.comments.collectAsState()

    Scaffold { paddingValues ->
        Box(Modifier.padding(paddingValues)) {
            // UI for loading state
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(posts) { item ->
                        FeedListItem(item) {
                            selectedPost = item
                            coroutineScope.launch {
                                viewModel.fetchComments(item.id) // Fetch comments for selected post
                                sheetState.show()  // Show the bottom sheet after fetching comments
                            }
                        }
                    }
                }

                // ModalBottomSheet to display post details
                if (selectedPost != null) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            coroutineScope.launch {
                                sheetState.hide()
                                selectedPost = null // Clear selected post on dismiss
                            }
                        },
                        sheetState = sheetState
                    ) {
                        FeedDetailBottomSheet(selectedPost!!, comments, onDismiss = {
                            coroutineScope.launch { sheetState.hide() }
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun FeedListItem(item: Post, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = "https://picsum.photos/300/200",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = item.body, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun FeedDetailBottomSheet(
    post: Post,
    comments: List<Comment>,
    onDismiss: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Post Title
        Text(
            text = post.title,
            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Post Image
        AsyncImage(
            model = "https://picsum.photos/300/200",
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp)
        )

        // Post Body
        Text(
            text = post.body,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(16.dp)
        )

        // Comments Section Title
        Text(
            text = "Comments:",
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )

        // Comments List
        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(comments) { comment ->
                CommentItem(comment)
            }
        }

        // Close button for dismissing the bottom sheet
        Button(
            onClick = onDismiss,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text("Close")
        }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = comment.email, // Email at the top
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
            Text(
                text = comment.name, // Comment title
                style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = comment.body, // Comment body text
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
        }
    }
}