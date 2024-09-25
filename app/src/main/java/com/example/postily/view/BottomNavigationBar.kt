package com.example.postily.view

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.postily.view.BottomNavItems

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItems.Feed,
        BottomNavItems.Albums,
        BottomNavItems.Tasks,
        BottomNavItems.Profile
    )

    NavigationBar {
        val currentBackStackEntry = navController.currentBackStackEntryAsState()
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentBackStackEntry.value?.destination?.route == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}