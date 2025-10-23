package com.example.karen_and.screens.login

data class LoginState (
    val email: String = "",
    val password: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,

    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
)