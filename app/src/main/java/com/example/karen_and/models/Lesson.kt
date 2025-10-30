package com.example.karen_and.models

data class Lesson(
    val id: Int,
    val subjectId: Int,
    val date: String,
    val status: LessonStatus,
    val contents: List<Content>
)