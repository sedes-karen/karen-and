package com.example.karen_and.models

import com.google.gson.annotations.SerializedName


enum class UserStatusEnum {
    @SerializedName("created") CREATED,
    @SerializedName("enabled") ENABLED,
    @SerializedName("disabled") DISABLED
}

enum class UserType {
    @SerializedName("teacher") TEACHER,
    @SerializedName("student") STUDENT;

}
data class UserModel(
    val id: Int,
    val name: String,
    val lastname: String,
    val email: String,
    val password: String = "",
    val user_status_id: Int?,
    val code_register: String?,
    val code_recovery: String?,
    val status: UserStatus,
    val typeUser: UserType
)


data class UserStatus(
    val name: UserStatusEnum
)
