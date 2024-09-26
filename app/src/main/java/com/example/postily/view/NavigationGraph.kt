package com.example.postily.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.postily.view.feed.FeedScreen
import com.example.postily.view.albums.AlbumsScreen
import com.example.postily.view.albums.AlbumDetailScreen
import com.example.postily.view.feed.FeedDetailScreen
import com.example.postily.view.profile.ProfileScreen
import com.example.postily.view.profile.friend.FriendDetailScreen
import com.example.postily.view.albums.photo.PhotoDetailScreen
import com.example.postily.view.tasks.TaskScreen

@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = "feed",
        modifier = Modifier.padding(paddingValues)
    ) {
        composable("feed") { FeedScreen(navController) }
        composable("feedDetail/{postId}") { backStackEntry ->
            FeedDetailScreen(navController, backStackEntry.arguments?.getString("postId"))
        }

        // Albums Screen
        composable("albums") { AlbumsScreen(navController) }

        // Detailed Album Screen for individual albums
        composable("albumDetail/{albumId}") { backStackEntry ->
            AlbumDetailScreen(navController, backStackEntry.arguments?.getString("albumId"))
        }

        // Photo detail screen
        composable("photoDetail/{photoId}") { backStackEntry ->
            PhotoDetailScreen(navController, backStackEntry.arguments?.getString("photoId"))
        }

        // Tasks Screen
        composable("tasks") { TaskScreen(navController) }

        // Profile (Me) Screen
        composable("profile") { ProfileScreen(navController) }

        // Friend Details
        composable("friendDetail/{friendId}") { backStackEntry ->
            FriendDetailScreen(navController, backStackEntry.arguments?.getString("friendId"))
        }
    }
}