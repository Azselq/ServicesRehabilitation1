package com.example.servicesrehabilitation

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.servicesrehabilitation.domain.RetrofitInstance
import com.example.servicesrehabilitation.domain.User
import com.example.servicesrehabilitation.login.LoginViewModel
import com.example.servicesrehabilitation.login.UserRepository
import com.example.servicesrehabilitation.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewModel(private val db: AppDatabase): ViewModel() {
    private val _userInfo = MutableLiveData<User>()
    val userInfo: LiveData<User> = _userInfo
    val authService = RetrofitInstance.authService
    val userRepository = UserRepository(authService)


    fun getUserInfo(token: String) {
        viewModelScope.launch {
            val response = userRepository.getCurrentUser(token)
            Log.d("test", "test response : ${response}")
            if (response.isSuccessful) {
                _userInfo.value = response.body()
                Log.d("test", "test getUser : ${_userInfo.value}")
            } else {
                // Обработайте ошибку
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


