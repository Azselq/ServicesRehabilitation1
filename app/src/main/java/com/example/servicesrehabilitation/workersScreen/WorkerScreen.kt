package com.example.servicesrehabilitation.workersScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.servicesrehabilitation.R
import com.example.servicesrehabilitation.room.AppDatabase

@Composable
fun WorkerScreen(
    navController: NavController, serviceType: String, appDatabase: AppDatabase
) {
    val factory = WorkerViewModelFactory(appDatabase)
    val viewModel: WorkerViewModel = viewModel(factory = factory)
    val workerInfo = viewModel.workerInfo.collectAsState()
    val backGroundColor = colorResource(id = R.color.custom_light_blue)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(backGroundColor),
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
        ) {
            if (it.workerService == serviceType) {
                WorkerItem(workerInfo = it) {
                    println("Нажатие на исполнителя с ID: ${it}")
                    navController.navigate("detail_worker_screen/${it.id}")
                }
            }
        }
    }
}
