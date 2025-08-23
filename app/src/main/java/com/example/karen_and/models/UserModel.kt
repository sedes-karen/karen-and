package com.example.karen_and.models

data class UserModel(
    val id: Int,
    val nombre: String,
    val apellido: String,
    val email: String,
    val password: String?,
    val code_register: String?,
    val code_recovery: String?,
    val active: Int
)
