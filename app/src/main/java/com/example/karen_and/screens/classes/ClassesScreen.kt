package com.example.karen_and.screens.classes

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.karen_and.R

@Composable
fun ClassesScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(stringResource(R.string.classes))
    }
}