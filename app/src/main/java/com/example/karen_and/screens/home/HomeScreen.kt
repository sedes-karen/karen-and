package com.example.karen_and.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Text("Home 1", style = MaterialTheme.typography.titleLarge)
        Text("Home 2", style = MaterialTheme.typography.titleLarge)
        Text("Home 3", style = MaterialTheme.typography.titleLarge)
        Text("Home 4", style = MaterialTheme.typography.titleLarge)
        Text("Home 5", style = MaterialTheme.typography.titleLarge)
        Text("Home 6", style = MaterialTheme.typography.titleLarge)
        Text("Home 7", style = MaterialTheme.typography.titleLarge)
        Text("Home 8", style = MaterialTheme.typography.titleLarge)
        Text("Home", style = MaterialTheme.typography.titleLarge)
        Text("Home", style = MaterialTheme.typography.titleLarge)
        Text("Home", style = MaterialTheme.typography.titleLarge)
    }
}