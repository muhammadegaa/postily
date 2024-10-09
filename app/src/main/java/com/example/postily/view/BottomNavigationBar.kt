package com.example.postily.view

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.app.NotificationCompat.Style
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
                colors = NavigationBarItemColors(
                    selectedTextColor = Teal200,
                    selectedIndicatorColor = Color.Transparent,
                    unselectedTextColor = Color.Black,
                    disabledIconColor = Color.Gray,
                    disabledTextColor = Color.Gray,
                    selectedIconColor = Teal200,
                    unselectedIconColor = Color.Black
                ),
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