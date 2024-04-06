package com.example.servicesrehabilitation.workersScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.servicesrehabilitation.navigation.AppNavGraph
import kotlinx.coroutines.delay
import java.util.Timer

@Composable
fun WorkerScreen(
    navController: NavController,
    serviceType: String
) {
    val viewModel: WorkerViewModel = viewModel()
    val workerInfo = viewModel.workerInfo.collectAsState()
    //viewModel.getAllWorker()
    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 80.dp
        ),

    ) {
        items(
            items = workerInfo.value,
            key = { it.id }
        ){
            if(it.workerService == serviceType) {
                WorkerItem(workerInfo = it){
                    println("Нажатие на исполнителя с ID: ${it}")
                    navController.navigate("detail_worker_screen/${it.id}")
                }
            }
        }
    }
}
