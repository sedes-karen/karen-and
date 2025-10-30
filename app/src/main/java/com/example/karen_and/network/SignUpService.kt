package com.example.karen_and.network

import com.example.karen_and.RetrofitClient
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpApi {
    @POST("auth/signUp")
    suspend fun signUp(@Body body: SingUpRequest): SignUpResponse
}

data class SingUpRequest(val email: String, val password: String, val name: String, val lastname: String, val birthday: String)

data class SignUpResponse(
    val userId: String,
    val email: String,
    val name: String,
    val lastname: String,
    val birthday: String,
    val token: String
)

object SignUpService {
    private val api: SignUpApi = RetrofitClient.create(SignUpApi::class.java)
    private val registeredEmails = mutableSetOf("test@gmail.com")

    fun emailExists(email: String): Boolean {
        return registeredEmails.contains(email)
    }

    suspend fun signUp(email: String, password: String, name: String, lastname: String, birthday: String): Result<SignUpResponse> =
        runCatching { api.signUp(SingUpRequest(email, password, name, lastname, birthday)) }
}