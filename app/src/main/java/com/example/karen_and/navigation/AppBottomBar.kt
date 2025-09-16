package com.example.karen_and.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.karen_and.R
import com.example.karen_and.navigation.Routes

data class BottomNavItem(
    val route: String,
    val label: Int,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private val bottomNavItems = listOf(
    BottomNavItem(Routes.HOME, R.string.home, Icons.Filled.Home),
    BottomNavItem(Routes.CHAT, R.string.chat, Icons.Filled.ChatBubbleOutline),
    BottomNavItem(Routes.PROFILE, R.string.profile, Icons.Filled.Person),
)

@Composable
fun AppBottomBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar {
        bottomNavItems.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
    icon = { Icon(item.icon, contentDescription = stringResource(item.label)) },
                label = { Text(stringResource(item.label)) }
            )
        }
    }
}

/** helper para saber si una ruta usa la bottom/top bar */
val appBarRoutes = setOf(Routes.HOME, Routes.CHAT, Routes.PROFILE, Routes.CLASSES)
