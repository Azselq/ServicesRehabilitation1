package com.example.servicesrehabilitation.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.servicesrehabilitation.domain.AuthToken
import com.example.servicesrehabilitation.domain.RetrofitInstance
import com.example.servicesrehabilitation.domain.User
import com.example.servicesrehabilitation.room.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val db: AppDatabase) : ViewModel() {
    val authService = RetrofitInstance.authService
    val userRepository = UserRepository(authService)
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Empty)
    val loginState: StateFlow<LoginState> = _loginState
    private val users = MutableLiveData <User>()

    init {
        checkUserLoggedIn()
    }
    private fun checkUserLoggedIn() {
        viewModelScope.launch {
            val authToken = db.authTokenDao().getAuthToken()
            if (authToken?.token != null) {
                _loginState.value = LoginState.AlreadyLoggedIn
            }
        }
    }
    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            val authToken = userRepository.loginUser(username, password)
            if (authToken != null) {
                db.authTokenDao().saveAuthToken(com.example.servicesrehabilitation.room.AuthToken(token = authToken.accessToken ?: ""))
                Log.d("test", "${db.authTokenDao().getAuthToken()}")
                _loginState.value = LoginState.Success(authToken)
            } else {
                _loginState.value = LoginState.Error("Authentication failed")
            }
        }
    }



        sealed class LoginState {
            object Empty : LoginState()
            object Loading : LoginState()
            object AlreadyLoggedIn : LoginState()
            data class Success(val authToken: AuthToken) : LoginState()
            data class Error(val message: String) : LoginState()
    }
}

class LoginViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
