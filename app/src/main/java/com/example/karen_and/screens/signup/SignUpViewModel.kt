package com.example.karen_and.screens.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.karen_and.R
import com.example.karen_and.navigation.Routes
import com.example.karen_and.ui.ui_events.UIEvents
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.karen_and.network.SignUpService
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

    fun onNameChange(newName: String) {
        _state.update {
            val updated = it.copy(name = newName)
            updated.copy(isFormValid = validate(updated))
        }
    }

    fun onLastnameChange(newLastname: String) {
        _state.update {
            val updated = it.copy(lastname = newLastname)
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

    fun nextStep() {
        if (_state.value.currentStep < 4) {
            _state.value = _state.value.copy(currentStep = _state.value.currentStep + 1)
        }
    }

    fun previousStep() {
        if (_state.value.currentStep > 1) {
            _state.update { it.copy(currentStep = it.currentStep - 1) }
        }
    }

    fun submit() {
        if (_state.value.isFormValid) {
            viewModelScope.launch {
                val state = _state.value

                if (!validate(state)) {
                    _events.emit(UIEvents.ShowSnackbar("Por favor, completá todos los campos correctamente"))
                    return@launch
                }

                _state.update { it.copy(isLoading = true) }

                try {
                    if (SignUpService.emailExists(state.email)) {
                        _events.emit(UIEvents.ShowSnackbar("Email en uso"))
                        _state.update { it.copy(isLoading = false) }
                        return@launch
                    }

                    val result = SignUpService.signUp(
                        _state.value.name,
                        _state.value.lastname,
                        _state.value.birthday,
                        _state.value.email,
                        _state.value.password
                    )

                    result
                        .onSuccess {
                            _events.emit(UIEvents.ShowSnackbar("Usuario registrado correctamente"))
                            _events.emit(UIEvents.Navigate(Routes.HOME))
                        }
                        .onFailure { error ->
                            val msg = when {
                                error.message?.contains(
                                    "email",
                                    ignoreCase = true
                                ) == true -> "Email en uso"

                                else -> "Error al registrar usuario"
                            }
                            _events.emit(UIEvents.ShowSnackbar(msg))
                        }

                } catch (e: Exception) {
                    _events.emit(UIEvents.ShowSnackbar("Error inesperado: ${e.message}"))
                }

                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    fun isValidBirthday(dateStr: String): Boolean {
        return try {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            dateFormat.isLenient = false // fuerza formato estricto (no 32/13/2020)

            val parsedDate = dateFormat.parse(dateStr)
            val today = Calendar.getInstance().time

            parsedDate != null && parsedDate.before(today)
        } catch (e: Exception) {
            false
        }
    }


    private fun validate(state: SignUpState): Boolean {
        return when (state.currentStep) {
            1 -> {
                val validName = state.name.isNotBlank()
                val validLastname = state.lastname.isNotBlank()
                _state.update {
                    it.copy(
                        nameError = if (!validName) "El nombre es obligatorio" else null,
                        lastnameError = if (!validLastname) "El apellido es obligatorio" else null
                    )
                }
                validName && validLastname
            }

            2 -> {
                val validBirthday = isValidBirthday(state.birthday)
                _state.update {
                    it.copy(birthdayError = if (!validBirthday) "Fecha inválida" else null)
                }
                validBirthday
            }

            3 -> {
                val validEmail = Patterns.EMAIL_ADDRESS.matcher(state.email).matches()
                _state.update {
                    it.copy(emailError = if (!validEmail) "Email inválido" else null)
                }
                validEmail
            }

            4 -> {
                val validPassword = state.password.length >= 6
                _state.update {
                    it.copy(passwordError = if (!validPassword) "La contraseña debe tener al menos 6 caracteres" else null)
                }
                validPassword
            }

            else -> false
        }
     }
}
