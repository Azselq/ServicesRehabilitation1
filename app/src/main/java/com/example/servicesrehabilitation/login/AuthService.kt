package com.example.servicesrehabilitation.login

import com.example.servicesrehabilitation.domain.AuthToken
import com.example.servicesrehabilitation.domain.ForumPost
import com.example.servicesrehabilitation.domain.RehabilitationInfo
import com.example.servicesrehabilitation.domain.Service
import com.example.servicesrehabilitation.domain.User
import com.example.servicesrehabilitation.domain.WorkerInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @FormUrlEncoded
    @POST("register/")
    suspend fun registerUser(@Field("username") username: String, @Field("password") password: String): Response<User>
    @FormUrlEncoded
    @POST("token")
    suspend fun loginUser(@Field("username") username: String, @Field("password") password: String): Response<AuthToken>
    @GET("users")
    suspend fun getAllUsers(): Call<User>
    @GET("users/me/")
    suspend fun getCurrentUser(@Header("Authorization") token: String): Response<User>
}


interface WorkersService {
    @GET("workers/")
    suspend fun getAllWorkers(): Response<List<WorkerInfo>>
}

interface RehabilitationService {
    @GET("rehabilitation/")
    suspend fun getAllRehabilitation(): Response<List<RehabilitationInfo>>
}

interface ForumService {
    @GET("forum_posts/")
    suspend fun getAllForumPost(): Response<List<ForumPost>>
}

interface AppointmentService {
    @POST("/appointments/")
        suspend fun createService(@Body service: Service): Response<Service>
    @GET("/appointments/")
        suspend fun getService(): Response<List<Service>>
}