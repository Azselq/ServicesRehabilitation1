package com.example.servicesrehabilitation.forumScreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.servicesrehabilitation.domain.ForumPost
import com.example.servicesrehabilitation.domain.RetrofitInstanceForForum
import com.example.servicesrehabilitation.domain.RetrofitInstanceForWorkers
import com.example.servicesrehabilitation.domain.WorkerInfo
import com.example.servicesrehabilitation.login.ForumRepository
import com.example.servicesrehabilitation.login.WorkerRepository
import com.example.servicesrehabilitation.workersScreen.LoadingState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForumViewModel : ViewModel(){
    val forumService = RetrofitInstanceForForum.forumService
    val forumRepository = ForumRepository(forumService)
    private val _forumPosts = MutableStateFlow<List<ForumPost>>(emptyList())
    val forumPosts: StateFlow<List<ForumPost>> = _forumPosts.asStateFlow()
    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState.LOADING)
    val loadingState: StateFlow<LoadingState> = _loadingState.asStateFlow()
    init{
        getAllForum()
    }


    fun getAllForum(){
        viewModelScope.launch {
            val response = forumRepository.getAllWorkers()
            Log.d("test", "test response : ${response}")
            if (response.isSuccessful) {
                _forumPosts.value = response.body()!!
                _loadingState.value = LoadingState.SUCCESS
                Log.d("test", "test getUser : ${_forumPosts.value}")
            } else {
                _loadingState.value = LoadingState.ERROR
            }
        }
    }

}