package com.example.servicesrehabilitation.login

import android.content.Context
import android.util.Log
import com.example.servicesrehabilitation.domain.AppointmentModel
import com.example.servicesrehabilitation.domain.AppointmentResponse
import com.example.servicesrehabilitation.domain.AuthToken
import com.example.servicesrehabilitation.domain.ForumPost
import com.example.servicesrehabilitation.domain.RehabilitationInfo
import com.example.servicesrehabilitation.domain.User
import com.example.servicesrehabilitation.domain.WorkerInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body


class UserRepository( private val authService: AuthService) {
    suspend fun registerUser(username: String, password: String): Response<User> {
        return authService.registerUser(username, password)
    }
    suspend fun loginUser(username: String, password: String): AuthToken? {
        Log.d("test", username + password)
        val response = authService.loginUser(username, password)
        Log.d("test", "$response ${response.body()}")
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        } else {
            Log.d("LoginError", "Error: ${response.errorBody()?.string()}")
            return null
        }
    }

    suspend fun getCurrentUser(token: String) = authService.getCurrentUser("Bearer $token")

    suspend fun getAllUser(): Call<User> {
        return authService.getAllUsers()
    }
}



class WorkerRepository( private val workersService: WorkersService) {

    suspend fun getAllWorkers(): Response<List<WorkerInfo>>{
        return workersService.getAllWorkers()
    }
    suspend fun getForumPost(post_id: Int): Response<WorkerInfo> {
        return workersService.getWorker(post_id)
    }
}

class RehabilitationRepository( private val rehabilitationService: RehabilitationService) {

    suspend fun getAllWorkers(): Response<List<RehabilitationInfo>>{
        return rehabilitationService.getAllRehabilitation()
    }
}


class ForumRepository( private val forumService: ForumService) {

    suspend fun getAllWorkers(): Response<List<ForumPost>>{
        return forumService.getAllForumPost()
    }
    suspend fun getForumPost(post_id: Int): Response<ForumPost> {
        return forumService.getForumPost(post_id)
    }
}

class ServiceRepository( private val appointmentService: AppointmentService) {

    suspend fun getService(): Response<List<AppointmentResponse>>{
        return appointmentService.getService()
    }

    suspend fun createService(service: AppointmentModel): Response<AppointmentResponse>{
        return appointmentService.createService(service)
    }
    suspend fun deleteService(appointmentId: Int): Response<Unit> {
        return appointmentService.deleteService(appointmentId)
    }
}