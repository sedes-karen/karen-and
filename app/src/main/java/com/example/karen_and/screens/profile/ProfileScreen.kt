package com.example.karen_and.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.karen_and.R

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        Text(stringResource(R.string.profile) + "1", style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.profile) + "2", style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.profile) + "3", style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.profile) + "4", style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.profile) + "5", style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.profile) + "6", style = MaterialTheme.typography.titleLarge)
    }
}