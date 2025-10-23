package com.example.karen_and.screens.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karen_and.network.LoginService
import com.example.karen_and.ui.ui_events.UIEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.karen_and.screens.login.LoginState


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

    fun submit(navigateToHome: () -> Unit) {

        // --- SOLUCIÓN TEMPORAL PARA PROBAR LA NAVEGACIÓN ---
        // Llama a la navegación directamente para que el botón funcione sin la API.
        navigateToHome()

        //TODO: validar que el mail tenga formato y la contraseña este escrita
    fun submit() {
        if (_state.value.isFormValid) {
            viewModelScope.launch {
                _state.update { it.copy(isLoading = true) }

                val result = LoginService.login(_state.value.email, _state.value.password)

                result
                    .onSuccess {
                        _events.emit(UIEvents.ShowSnackbar("Sesión iniciada correctamente"))
                    }
                    .onFailure { error ->
                        _events.emit(UIEvents.ShowSnackbar(error.message ?: "Ocurrió un error"))
                    }

            //@TODO: remplazar el api call de test al endpoint correspondiente
            // val result = LoginService.login(_state.value.email, _state.value.password)
            val result = LoginService.test()

            result.onSuccess {
                //TODO: guardar el token y datos del user y redirigir a la home screen

                Log.i("LOGIN::::", "Todo bien")
                // navigateToHome() // Esta línea ya no es necesaria aquí
                _state.update { it.copy(isLoading = false) }
            }
        } else {
            viewModelScope.launch {
                _events.emit(UIEvents.ShowSnackbar("Email o contraseña incorrectas. Por favor, revisar los campos"))
            }
        }
    }

    private fun validate(state: LoginState): Boolean {
        val emailOk = Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
        val passOk = state.password.length >= 6
        return emailOk && passOk && !state.isLoading
    }

}