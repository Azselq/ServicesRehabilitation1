package com.example.servicesrehabilitation.workersScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicesrehabilitation.domain.RehabilitationInfo
import com.example.servicesrehabilitation.domain.RetrofitInstanceForRehabilitation
import com.example.servicesrehabilitation.domain.RetrofitInstanceForWorkers
import com.example.servicesrehabilitation.domain.WorkerInfo
import com.example.servicesrehabilitation.login.RehabilitationRepository
import com.example.servicesrehabilitation.login.WorkerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RehabilitationScreenViewModel: ViewModel() {

    val rehabilitationService = RetrofitInstanceForRehabilitation.rehabilitationService
    val rehabilitationRepository = RehabilitationRepository(rehabilitationService)
    private val _rehabilitationInfo = MutableStateFlow<List<RehabilitationInfo>>(emptyList())
    val rehabilitationInfo: StateFlow<List<RehabilitationInfo>> = _rehabilitationInfo.asStateFlow()
    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.LOADING)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()

    init {
        getAllRehabilitation()
    }

    fun getAllRehabilitation() {
        viewModelScope.launch {
            val response = rehabilitationRepository.getAllWorkers()
            Log.d("test", "test response : ${response}")
            if (response.isSuccessful) {
                _rehabilitationInfo.value = response.body()!!
                _loadingState.value = LoadingState.SUCCESS
                Log.d("test", "test getUser : ${_rehabilitationInfo.value}")
            } else {
                _loadingState.value = LoadingState.ERROR
            }
        }
    }
}

