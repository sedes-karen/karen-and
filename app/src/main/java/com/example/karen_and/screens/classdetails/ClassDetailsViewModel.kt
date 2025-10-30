package com.example.karen_and.screens.classdetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.karen_and.models.Lesson

class DetailsViewModel : ViewModel() {
    var showDialog by mutableStateOf(false)
        private set

    var selectedClass by mutableStateOf<Lesson?>(null)
        private set

    fun openDialog(clase: Lesson) {
        selectedClass = clase
        showDialog = true
    }

    fun closeDialog() {
        showDialog = false
        selectedClass = null
    }
}
