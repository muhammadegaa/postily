package com.example.postily.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
)

object BottomNavItems {
    val Feed = BottomNavItem(
        route = "feed",
        icon = Icons.Default.Home,
        title = "Feed"
    )
    val Albums = BottomNavItem(
        route = "albums",
        icon = Icons.Default.Folder,
        title = "Albums"
    )
    val Tasks = BottomNavItem(
        route = "tasks",
        icon = Icons.Default.List,
        title = "Tasks"
    )
    val Profile = BottomNavItem(
        route = "profile",
        icon = Icons.Default.Person,
        title = "Me"
    )
}
