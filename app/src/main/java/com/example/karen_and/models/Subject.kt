package com.example.karen_and.models

data class Subject(
    val id: Int,
    val name: String,
    val userId: Int,
    val lessons: List<Lesson>
)