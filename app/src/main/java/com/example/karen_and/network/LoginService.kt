package com.example.karen_and.network

import com.example.karen_and.RetrofitClient
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {
    @POST("auth/login")
    suspend fun login(@Body body: LoginRequest): LoginResponse

    @GET("users")
    suspend fun test()
}

data class LoginRequest(val email: String, val password: String)

data class LoginResponse(
    val userId: String,
    val email: String,
    val token: String
)

object LoginService {
    private val api: LoginApi = RetrofitClient.create(LoginApi::class.java)

    suspend fun login(email: String, password: String): Result<LoginResponse> =
        runCatching { api.login(LoginRequest(email, password)) }

    suspend fun test() =
        runCatching { api.test() }
}
