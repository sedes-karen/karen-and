package com.example.karen_and.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun LogoutButton(onLogout: () -> Unit) {
    NavigationDrawerItem(
        label = { Text("Cerrar sesión") },
        selected = false,
        onClick = onLogout,
        colors = NavigationDrawerItemDefaults.colors(unselectedContainerColor = Color(0x996A1B9A), unselectedTextColor = Color.White)
    )
}
