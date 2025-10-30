package com.example.karen_and.screens.signup

data class SignUpState(
    val name: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val birthday: String = "",

    val nameError: String? = null,
    val lastnameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val birthdayError: String? = null,

    val isLoading: Boolean = false,
    val isFormValid: Boolean = false,
    val currentStep: Int = 1
)
