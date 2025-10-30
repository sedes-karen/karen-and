package com.example.karen_and.screens.teacher_screens.accept_student

import com.example.karen_and.models.UserModel
import com.example.karen_and.models.UserStatus
import kotlin.collections.listOf

data class BatchResult(
    val successes: Int = 0,
    val failures: Map<Int, String> = emptyMap()
)

data class AcceptStudentState (
    val students: List<UserModel> = listOf(),
    val studentsSelected: Set<Int> = emptySet(),
    val isSubmitting: Boolean = false,
    val lastBatch: BatchResult? = null,
    val errorMessage: String? = null,
    val isLoadingGetUsers: Boolean = false,
)