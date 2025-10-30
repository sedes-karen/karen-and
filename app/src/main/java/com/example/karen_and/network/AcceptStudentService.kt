package com.example.karen_and.network

import com.example.karen_and.RetrofitClient
import com.example.karen_and.models.UserModel
import com.example.karen_and.models.UserStatusEnum
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface AcceptStudentApi {
    @POST("teacher/students/{studentId}/status")
    suspend fun acceptStudent(

        @Path("studentId") studentId: Int,
        @Body body: AcceptStudentRequest): AcceptStudentResponse

    @GET("/api/users")
    suspend fun getUsers(): GetUsersResponse
}


data class AcceptStudentRequest(val status: UserStatusEnum)

data class AcceptStudentResponse(
    val userId: String,
    val email: String,
    val token: String
)
data class GetUsersResponse(
    val ok: Boolean,
    val data: List<UserModel>
)

object AcceptStudentService {
    private val api: AcceptStudentApi = RetrofitClient.create(AcceptStudentApi::class.java)

    suspend fun acceptStudent(studentId: Int, status: UserStatusEnum): Result<AcceptStudentResponse> =
        runCatching { api.acceptStudent(studentId, AcceptStudentRequest(status)) }

    suspend fun getUsers() =
        runCatching { api.getUsers() }
}
