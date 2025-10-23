package com.example.karen_and.screens.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karen_and.R
import com.example.karen_and.ui.ui_events.UIEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.karen_and.network.SignUpService
import kotlin.text.isNotBlank

class SignUpViewModel : ViewModel() {
    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state
    private val _events = MutableSharedFlow<UIEvents>()
    val events = _events.asSharedFlow()

    fun onEmailChange(newEmail: String) {
        _state.update {
            val updated = it.copy(email = newEmail)
            updated.copy(isFormValid = validate(updated))
        }
    }

    fun onPasswordChange(newPass: String) {
        _state.update {
            val updated = it.copy(password = newPass)
            updated.copy(isFormValid = validate(updated))
        }
    }

    fun onBirthdayChange(newBirthday: String) {
        _state.update {
            val updated = it.copy(birthday = newBirthday)
            updated.copy(isFormValid = validate(updated))
        }
    }

    fun submit() {
        if (_state.value.isFormValid) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                val result = SignUpService.signUp(_state.value.email, _state.value.password, _state.value.name, _state.value.birthday)

                result
                    .onSuccess {
                        _events.emit(UIEvents.ShowSnackbar(R.string.success_message_login.toString()))
                    }
                    .onFailure { error ->
                        _events.emit(UIEvents.ShowSnackbar(error.message ?: R.string.error_message.toString()))
                    }

                _state.update { it.copy(isLoading = false) }
            }
        } else {
            viewModelScope.launch {
                _events.emit(UIEvents.ShowSnackbar("Email o contraseña incorrectas. Por favor, revisar los campos"))
            }
        }
    }

    private fun validate(state: SignUpState): Boolean {
        val emailOk = Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
        val passOk = state.password.length >= 6
        val birthdayOk = state.birthday.isNotBlank()
        val nameOk = state.birthday.isNotBlank()

        return emailOk && passOk && birthdayOk && nameOk && !state.isLoading
    }

}