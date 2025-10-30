package com.example.karen_and.screens.login

import android.util.Patterns
import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karen_and.ui.ui_events.UIEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.karen_and.network.LoginService
import com.example.karen_and.navigation.Routes

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state
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

    fun submit() {
        if (_state.value.isFormValid) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                val loginResult = LoginService.login(_state.value.email, _state.value.password)

                loginResult
                    .onSuccess {
                        _events.emit(UIEvents.ShowSnackbar("Sesión iniciada correctamente"))
                        testApiCall()
                    }
                    .onFailure { error ->
                        _events.emit(UIEvents.ShowSnackbar(error.message ?: "Ocurrió un error en el login"))
                        _state.update { it.copy(isLoading = false) }
                    }
            }
        } else {
            viewModelScope.launch {
                _events.emit(UIEvents.ShowSnackbar("Email o contraseña incorrectas. Por favor, revisar los campos"))
            }
        }
    }

    private fun testApiCall() {
        viewModelScope.launch {
            val testResult = LoginService.test()

            testResult.onSuccess {
                Log.i("LOGIN::::", "Todo bien en el test")
                _state.update { it.copy(isLoading = false) }
            }
                .onFailure {
                    Log.e("LOGIN::::", "Error en el test")
                    _state.update { it.copy(isLoading = false) }
                }
        }
    }

    private fun validate(state: LoginState): Boolean {
        val emailOk = Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
        val passOk = state.password.length >= 6
        return emailOk && passOk && !state.isLoading
    }

}