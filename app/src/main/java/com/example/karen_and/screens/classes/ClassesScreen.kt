package com.example.karen_and.screens.classes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.karen_and.models.ClassModel
import com.example.karen_and.services.ClassesService
import com.example.karen_and.ui.components.ClassListItem
import com.example.karen_and.viewmodels.ClassesViewModel

@Composable
fun ClassesScreen(modifier: Modifier) {
    val classesService = ClassesService()
    val viewModel: ClassesViewModel = viewModel {
        ClassesViewModel(classesService = classesService)
    }

    val classes by viewModel.classes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn {
            items(classes) { classItem ->
                ClassListItem(classItem)
            }
        }
    }
}

@Composable
fun ClassListItem(x0: ClassModel) {
    TODO("Not yet implemented")
}