package com.example.servicesrehabilitation.workersScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicesrehabilitation.domain.ForumPost
import com.example.servicesrehabilitation.domain.RetrofitInstanceForService
import com.example.servicesrehabilitation.domain.RetrofitInstanceForWorkers
import com.example.servicesrehabilitation.domain.Service
import com.example.servicesrehabilitation.domain.WorkerInfo
import com.example.servicesrehabilitation.login.ServiceRepository
import com.example.servicesrehabilitation.login.WorkerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

class WorkerViewModel : ViewModel(){
    val workerService = RetrofitInstanceForWorkers.workerService
    val workerRepository = WorkerRepository(workerService)
    private val _workerInfo = MutableStateFlow<List<WorkerInfo>>(emptyList())
    val workerInfo: StateFlow<List<WorkerInfo>> = _workerInfo.asStateFlow()
    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.LOADING)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()

    val appointmentService = RetrofitInstanceForService.service
    val appointmentRepository = ServiceRepository(appointmentService)

    init{
        getAllWorker()
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
    fun bookService(service: Service) {
        viewModelScope.launch {
                val response = appointmentRepository.createService(service)
                if (response.isSuccessful) {
                    // Обработка успешного ответа
                    Log.d("Booking", "Service booked successfully")
                } else {
                    // Обработка ошибки
                    Log.e("Booking", "Error booking service: ${response.errorBody()}")
                }
        }
    }
}
enum class LoadingState {
    LOADING, SUCCESS, ERROR
}