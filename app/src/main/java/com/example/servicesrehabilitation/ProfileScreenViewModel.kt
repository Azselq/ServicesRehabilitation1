package com.example.servicesrehabilitation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.servicesrehabilitation.domain.AppointmentResponse
import com.example.servicesrehabilitation.domain.RetrofitInstance
import com.example.servicesrehabilitation.domain.RetrofitInstanceForService
import com.example.servicesrehabilitation.domain.User
import com.example.servicesrehabilitation.login.LoginViewModel
import com.example.servicesrehabilitation.login.ServiceRepository
import com.example.servicesrehabilitation.login.UserRepository
import com.example.servicesrehabilitation.room.AppDatabase
import com.example.servicesrehabilitation.workersScreen.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val db: AppDatabase): ViewModel() {
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> = _userInfo
    val authService = RetrofitInstance.authService
    val userRepository = UserRepository(authService)
    val appointmentService = RetrofitInstanceForService.service
    val appointmentRepository = ServiceRepository(appointmentService)

    private val _appointmentInfo = MutableStateFlow<List<AppointmentResponse>>(emptyList())
    val appointmentInfo: StateFlow<List<AppointmentResponse>> = _appointmentInfo
    init{
        getAllAppointment()
    }

    fun getUserInfo(token: String) {
        viewModelScope.launch {
            val response = userRepository.getCurrentUser(token)
            Log.d("test", "test response : ${response}")
            if (response.isSuccessful) {
                _userInfo.value = response.body()
                Log.d("test", "test getUser : ${_userInfo.value}")
            } else {
            }
        }
}

    fun loadUserInfo() {
        viewModelScope.launch {
            val tokenEntity = db.authTokenDao().getAuthToken()
            if (tokenEntity != null) {
                Log.d("test", "test token : ${tokenEntity.token}")
            }
            if (tokenEntity != null) {
                tokenEntity.token?.let { getUserInfo(it) }
            }
        }
    }

    fun deleteUserInfo(){
        viewModelScope.launch {
            db.authTokenDao().deleteAuthToken()
        }
    }

    fun getAllAppointment(){
        viewModelScope.launch {
            val response = appointmentRepository.getService()
            Log.d("test", "test response : ${response}")
            if (response.isSuccessful) {
                _appointmentInfo.value = response.body()!!
                //_loadingState.value = LoadingState.SUCCESS
                Log.d("test", "test getUser : ${_appointmentInfo.value}")
            } else {
                //_loadingState.value = LoadingState.ERROR
            }
        }
    }

    fun remove(appointmentId: Int){
        viewModelScope.launch {
            val response = appointmentRepository.deleteService(appointmentId)
            Log.d("test", "test response : ${response}")
            if (response.isSuccessful) {
                _appointmentInfo.value = _appointmentInfo.value.filter { it.id != appointmentId }
                Log.d("test", "test getUser : ${_appointmentInfo.value}")
            } else {
                //_loadingState.value = LoadingState.ERROR
            }
        }
    }
    class ProfileScreenViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProfileScreenViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProfileScreenViewModel(db) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}


