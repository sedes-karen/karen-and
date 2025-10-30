package com.example.karen_and.models

enum class UserRole { TEACHER, STUDENT }

enum class UserStatusEnum(val label: String) {
    CREATED("created"),
    ENABLED("enabled"),
    SUSPENDED("suspended");


    override fun toString() = label

}

data class UserStatus(
    val name: UserStatusEnum,
)

data class UserModel(
    val id: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val password: String = "",
    val user_status_id: Int?,
    val code_register: String?,
    val code_recovery: String?,
    val status: UserStatusEnum,
)
