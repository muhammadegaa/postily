package com.example.postily.view

import android.content.Intent
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
import com.example.postily.view.profile.ProfileScreen
import com.example.postily.view.profile.friend.FriendDetailScreen
import com.example.postily.view.albums.photo.PhotoDetailScreen
import com.example.postily.view.tasks.TaskScreen
import com.google.android.gms.auth.api.Auth

@Composable
fun NavigationGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    onGoogleSignIn: (Intent) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = "feed",
        modifier = Modifier.padding(paddingValues)
    ) {

        composable("splash") {
            SplashScreen(navController)
        }
        composable("auth") {
            AuthScreen(navController = navController, onGoogleSignIn = onGoogleSignIn)
        }

        // Feed screen, which lists posts
        composable("feed") {
            FeedScreen(navController)
        }

        // Albums screen, which lists all albums
        composable("albums") {
            AlbumsScreen(navController)
        }

        // AlbumDetail screen shows photos of the selected album
        composable("albumDetail/{albumId}") { backStackEntry ->
            val albumId = backStackEntry.arguments?.getString("albumId")
            AlbumDetailScreen(navController = navController, albumId = albumId)
        }

        // PhotoDetail screen shows a detailed view of a selected photo
        composable("photoDetail/{photoId}") { backStackEntry ->
            val photoId = backStackEntry.arguments?.getString("photoId")
            PhotoDetailScreen(navController = navController, photoId = photoId)
        }

        // Tasks screen, which lists ongoing and completed tasks
        composable("tasks") {
            TaskScreen(navController)
        }

        // Profile screen shows the user's details (Me tab)
        composable("profile") {
            ProfileScreen(navController)
        }

        // FriendDetail screen shows details of the selected friend from the profile
        composable("friendDetail/{friendId}") { backStackEntry ->
            val friendId = backStackEntry.arguments?.getString("friendId")
            FriendDetailScreen(navController = navController, friendId = friendId)
        }
    }
}

