package com.example.karen_and.screens.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karen_and.models.ClassModel
import com.example.karen_and.network.services.ClassesService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ClassesViewModel(private val classesService: ClassesService) : ViewModel() {

    private val _classes = MutableStateFlow<List<ClassModel>>(emptyList())
    val classes: StateFlow<List<ClassModel>> = _classes

    init {
        loadClasses()
    }

    private fun loadClasses() {
        viewModelScope.launch {
            val classList = classesService.getMockClasses()
            _classes.value = classList
        }
    }
}