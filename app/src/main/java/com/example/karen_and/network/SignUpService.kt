package com.example.karen_and.network

import com.example.karen_and.RetrofitClient
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {
    @POST("auth/signUp")
    suspend fun signUp(@Body body: SingUpRequest): SignUpResponse
}

data class SingUpRequest(
    val name: String,
    val lastname: String,
    val email: String,
    val password: String
)

data class SignUpResponse(
    val userId: String,
    val email: String,
    val name: String,
    val lastname: String
)

object SignUpService {
    private val api: SignUpApi = RetrofitClient.create(SignUpApi::class.java)
    private val registeredEmails = mutableSetOf("test@gmail.com")

    fun emailExists(email: String): Boolean {
        return registeredEmails.contains(email)
    }

    suspend fun signUp(name: String, lastname: String, email: String, password: String): Result<SignUpResponse> =
        runCatching { api.signUp(SingUpRequest(name, lastname, email, password)) }
}