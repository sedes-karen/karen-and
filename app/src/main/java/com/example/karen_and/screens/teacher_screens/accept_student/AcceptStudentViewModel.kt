package com.example.karen_and.screens.teacher_screens.accept_student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karen_and.models.UserStatusEnum
import com.example.karen_and.network.services.AcceptStudentService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AcceptStudentViewModel : ViewModel() {
    private val _state = MutableStateFlow(AcceptStudentState())
    val state: StateFlow<AcceptStudentState> = _state

    fun toggleSelection(id: Int) {
        _state.update { s ->
            val newSet = if (id in s.studentsSelected) {
                s.studentsSelected - id
            } else {
                s.studentsSelected + id
            }
            s.copy(studentsSelected = newSet)
        }
    }

    fun clearSelection() {
        _state.update { s -> s.copy(studentsSelected = emptySet()) }
    }

    fun acceptSelectedSequential(targetStatus: UserStatusEnum = UserStatusEnum.ENABLED) {
        val ids = _state.value.studentsSelected.toList()
        if (ids.isEmpty()) return
        _state.update { it.copy(isLoadingGetUsers = true) }

        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, lastBatch = null, errorMessage = null) }

            var ok = 0
            val failures = mutableMapOf<Int, String>()
            for (id in ids) {
                val res = AcceptStudentService.acceptStudent(id, targetStatus)
                if (res.isSuccess) ok++ else failures[id] = res.exceptionOrNull()?.message ?: "Unknown error"
            }

            val successIds = ids - failures.keys

            delay(500)
            _state.update {
                it.copy(
                    isSubmitting = false,
                    lastBatch = BatchResult(successes = ok, failures = failures),
                    studentsSelected = emptySet(),
                    students = state.value.students.filterNot { it.id in successIds },
                    isLoadingGetUsers = false
                )
            }
        }
    }

    fun getUsers() {
        _state.update { it.copy(isLoadingGetUsers = true) }

        viewModelScope.launch {
            val res = AcceptStudentService.getUsers()
            res.onSuccess { response ->
                val filtered = response.data.filter { user ->
                    user.status.name == UserStatusEnum.CREATED
                }

                _state.update {
                    it.copy(
                        students = filtered,
                        isLoadingGetUsers = false,
                    )
                }
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        isLoadingGetUsers = false,
                        errorMessage = e.message ?: "Error desconocido"
                    )
                }
            }
        }
    }

}