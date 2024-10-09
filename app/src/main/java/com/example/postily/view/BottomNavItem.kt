package com.example.postily.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.DynamicFeed
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Task
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import com.example.postily.R

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
)

object BottomNavItems {
    val Feed = BottomNavItem(
        route = "feed",
        icon = Icons.Default.DynamicFeed,
        title = "Feed"
    )
    val Albums = BottomNavItem(
        route = "albums",
        icon = Icons.Default.PhotoLibrary,
        title = "Albums"
    )
    val Tasks = BottomNavItem(
        route = "tasks",
        icon = Icons.Default.Task,
        title = "Tasks"
    )
    val Profile = BottomNavItem(
        route = "profile",
        icon = Icons.Default.Person,
        title = "Me"
    )
}
