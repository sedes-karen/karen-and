package com.example.karen_and.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karen_and.ui.ui_events.UIEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    // TODO: actualizar el state y conectarla a un textfield en la LoginScreen
    fun onPasswordChange(newPass: String) {

    }

    fun submit() {

        //TODO: validar que el mail tenga formato y la contraseña este escrita

        viewModelScope.launch {
            // esto muestra un popup cuando tocas el botoncito, pueden borrarlo
            _events.emit(UIEvents.ShowSnackbar(("Email: ${_state.value.email}, pass: ${_state.value.password}")))

            _state.update { it.copy(isLoading = true) }

            val result = LoginService.login(_state.value.email, _state.value.password)

            result.onSuccess {
                //TODO: guardar el token y datos del user y redirigir a la home screen
            }
                .onFailure {
                    //TODO: mostrar un snackbar/toast con un mensajito de error
                }
        }
    }


    private fun validate(state: LoginState): Boolean {
        val emailOk = Patterns.EMAIL_ADDRESS.matcher(state.email).matches() // verifica el formato del mail, osea algo@dominio.com
        val passOk = state.password.length >= 6
        return emailOk && passOk && !state.isLoading
    }

}