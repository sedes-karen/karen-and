package com.example.karen_and.screens.signup

data class SignUpState (
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val birthday: String = "",

    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
)