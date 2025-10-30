package com.example.karen_and.screens.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.karen_and.data.SessionStore
import com.example.karen_and.network.services.LoginService
import com.example.karen_and.ui.ui_events.UIEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val sessionStore: SessionStore
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state
    private val _events = MutableSharedFlow<UIEvents>()
    val events = _events.asSharedFlow()

    fun onEmailChange(newEmail: String) {
        _state.update {
            it.copy(email = newEmail, isFormValid = validate())
        }
    }

    fun onPasswordChange(newPass: String) {
        _state.update {
            it.copy(password = newPass, isFormValid = validate())
        }
    }

    fun submit(navigateToHome: () -> Unit) {
        if (_state.value.isFormValid) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                val result = LoginService.login(_state.value.email, _state.value.password)

                result
                    .onSuccess { data ->
                        sessionStore.saveSession(data.token, data.user)
                        navigateToHome()
                    }
                    .onFailure { error ->
                        Log.d("LOGIN::::", error.toString())
                        _events.emit(UIEvents.ShowSnackbar(error.message ?: "Ocurrió un error"))
                    }

                _state.update { it.copy(isLoading = false) }
            }
        } else {
            viewModelScope.launch {
                _events.emit(UIEvents.ShowSnackbar("Email o contraseña incorrectas. Por favor, revisar los campos"))
            }
        }
    }

    private fun validate(): Boolean {
        val emailOk = Patterns.EMAIL_ADDRESS.matcher(_state.value.email.trim()).matches()
        val passOk = _state.value.password.length >= 6
        return emailOk && passOk
    }

}

class LoginViewModelFactory(
    private val sessionStore: SessionStore
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(sessionStore) as T  // 👈 le pasa la dependencia
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}