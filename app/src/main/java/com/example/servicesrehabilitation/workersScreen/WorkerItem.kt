package com.example.servicesrehabilitation.workersScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.servicesrehabilitation.domain.WorkerInfo
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import com.example.servicesrehabilitation.domain.Service
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun WorkerItemDesc(
    navController: NavController, workerId: Int
) {
    val viewModel: WorkerViewModel = viewModel()
    val workerInfo = viewModel.workerInfo.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    when (loadingState.value) {
        LoadingState.LOADING -> {
            CircularProgressIndicator()
        }
        LoadingState.SUCCESS -> {
            Log.d("test2", "LoadingState  : ${workerInfo.value}")
            WorkerDetail(workerInfo.value[workerId-1])
        }
        LoadingState.ERROR -> {
            Text("Произошла ошибка при загрузке данных.")
        }
    }
}

@Composable
fun WorkerDetail(workerInfo: WorkerInfo){
    val showDialog = remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(8.dp, bottom = 80.dp)
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.background(Color.White)
        ) {
            WorkerHeaderDesc(workerInfo)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Информация об исполнителе",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(text = workerInfo.workerInfo,)
            Button(onClick = { showDialog.value = true }) {
                Text("Записаться на услугу")
            }
            if (showDialog.value) {
                AlertDialog(
                    onDismissRequest = { showDialog.value = false },
                    title = { Text("Запись на услугу") },
                    text = { BookingDialog(workerInfo = workerInfo) },
                    confirmButton = {
                        Button(onClick = { showDialog.value = false }) {
                            Text("Закрыть")
                        }
                    }
                )
            }
        }
    }
}
    @Composable
    fun WorkerItem(
        workerInfo: WorkerInfo,
        onClick: () -> Unit
    ) {
        Row(
            modifier = Modifier.clickable(onClick = onClick).padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
            modifier = Modifier
                .size(50.dp),
            painter = rememberAsyncImagePainter(model = workerInfo.workerResId),
            contentDescription = "null"
        )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = workerInfo.workerName)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = workerInfo.workerCost + "₽")
            }

            Icon(
                imageVector = Icons.Rounded.MoreVert,
                contentDescription = "null",

                )
        }
    }

    @Composable
    fun WorkerHeaderDesc(workerInfo: WorkerInfo) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
            modifier = Modifier
                .size(100.dp),
            painter = rememberAsyncImagePainter(model = workerInfo.workerResId),
            contentDescription = "null"
        )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = workerInfo.workerName)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Опыт работы - ${workerInfo.workerExperience}")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = "5", fontSize = 16.sp)
                }

            }
        }
    }

    @Composable
    fun BookingDialog(workerInfo: WorkerInfo) {
        val viewModel: WorkerViewModel = viewModel()
        var selectedDate by remember { mutableStateOf(LocalDate.now()) }
        var selectedTime by remember { mutableStateOf(LocalTime.now()) }
        val context = LocalContext.current

        Column {
            Button(onClick = {
                val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
                    selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
                }, selectedDate.year, selectedDate.monthValue - 1, selectedDate.dayOfMonth)

                datePickerDialog.show()
            }) {
                Text("Выберите дату")
            }
            Text("Выбранная дата: ${selectedDate.format(DateTimeFormatter.ISO_DATE)}")
            Button(onClick = {
                val timePickerDialog = TimePickerDialog(context, { _, hourOfDay, minute ->
                    selectedTime = LocalTime.of(hourOfDay, minute)
                }, selectedTime.hour, selectedTime.minute, true)

                timePickerDialog.show()
            }) {
                Text("Выберите время")
            }
            Text("Выбранное время: ${selectedTime.format(DateTimeFormatter.ISO_TIME)}")

            Button(onClick = {
                val service = Service(
                    id = 0,
                    user_token = "123123",
                    perfomer_name = "SDfsdf",
                    sevice_name = "asdad",
                    perfomer_id = 5,
                    date = "qweqeqwe"
                )
                viewModel.bookService(service)
            }) {
                Text("Записаться")
            }
        }
    }

