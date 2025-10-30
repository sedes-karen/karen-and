package com.example.karen_and.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LogoutButton(onLogout: () -> Unit) {
    NavigationDrawerItem(
        label = { Text("Cerrar sesión", color = MaterialTheme.colorScheme.error) },
        selected = false,
        onClick = onLogout
    )
}
