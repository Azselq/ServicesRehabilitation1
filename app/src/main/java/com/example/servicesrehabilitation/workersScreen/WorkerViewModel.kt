package com.example.servicesrehabilitation.workersScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.servicesrehabilitation.ProfileScreenViewModel
import com.example.servicesrehabilitation.domain.AppointmentModel
import com.example.servicesrehabilitation.domain.RetrofitInstanceForService
import com.example.servicesrehabilitation.domain.RetrofitInstanceForWorkers
import com.example.servicesrehabilitation.domain.WorkerInfo
import com.example.servicesrehabilitation.login.ServiceRepository
import com.example.servicesrehabilitation.login.WorkerRepository
import com.example.servicesrehabilitation.room.AppDatabase
import com.example.servicesrehabilitation.room.UserTokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WorkerViewModel(appDatabase: AppDatabase) : ViewModel(){
    val workerService = RetrofitInstanceForWorkers.workerService
    val workerRepository = WorkerRepository(workerService)
    private val _workerInfo = MutableStateFlow<List<WorkerInfo>>(emptyList())
    val workerInfo: StateFlow<List<WorkerInfo>> = _workerInfo.asStateFlow()
    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.LOADING)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()
    val appointmentService = RetrofitInstanceForService.service
    val appointmentRepository = ServiceRepository(appointmentService)
    val userToken = MutableStateFlow<String>("")
    val appDatabase = appDatabase
    init{
        getAllWorker()
        getAuthToken()
    }

    fun getAllWorker(){
        viewModelScope.launch {
            val response = workerRepository.getAllWorkers()
            Log.d("test", "test response : ${response}")
            if (response.isSuccessful) {
                _workerInfo.value = response.body()!!
                _loadingState.value = LoadingState.SUCCESS
                Log.d("test", "test getUser : ${_workerInfo.value}")
            } else {
                _loadingState.value = LoadingState.ERROR
            }
        }
    }
    fun bookService(service: AppointmentModel) {
        viewModelScope.launch {
                val response = appointmentRepository.createService(service)

                if (response.isSuccessful) {
                    // Обработка успешного ответа
                    Log.d("Booking", "Service booked successfully")
                } else {
                    // Обработка ошибки
                    Log.e("Booking", "Error booking service: ${response.errorBody()?.string()}")
                }
        }
    }

    fun getAuthToken(){
        viewModelScope.launch {
            userToken.value = appDatabase.authTokenDao().getAuthToken()?.token.toString()
        }
    }
}
class WorkerViewModelFactory(private val db: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorkerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorkerViewModel(db) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

enum class LoadingState {
    LOADING, SUCCESS, ERROR
}