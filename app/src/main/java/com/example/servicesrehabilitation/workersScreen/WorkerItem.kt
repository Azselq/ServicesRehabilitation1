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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import coil.compose.rememberAsyncImagePainter
import com.example.servicesrehabilitation.ProfileScreenViewModel
import com.example.servicesrehabilitation.R
import com.example.servicesrehabilitation.domain.AppointmentModel
import com.example.servicesrehabilitation.room.AppDatabase
import com.example.servicesrehabilitation.room.UserTokenRepository
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun WorkerItemDesc(
    navController: NavController, workerId: Int, appDatabase: AppDatabase
) {
    val factory = WorkerViewModelFactory(appDatabase)
    val viewModel: WorkerViewModel = viewModel(factory = factory)
    val workerInfo = viewModel.workerInfo.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    when (loadingState.value) {
        LoadingState.LOADING -> {
            CircularProgressIndicator()
        }
        LoadingState.SUCCESS -> {
            Log.d("test2", "LoadingState  : ${workerInfo.value}")
            WorkerDetail(workerInfo.value[workerId-1],viewModel)
        }
        LoadingState.ERROR -> {
            Text("Произошла ошибка при загрузке данных.")
        }
    }
}

@Composable
fun WorkerDetail(workerInfo: WorkerInfo, viewModel: WorkerViewModel){
    val showDialog = remember { mutableStateOf(false) }
    val backGroundColor = colorResource(id = R.color.custom_light_blue)
    Box(modifier = Modifier.fillMaxSize().background(backGroundColor)){
        Card(
        modifier = Modifier
            .padding(8.dp, bottom = 80.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Column(
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
                    text = { BookingDialog(workerInfo = workerInfo,
                        viewModel, onClose = { showDialog.value = false })},
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
    }

    @Composable
    fun WorkerItem(
        workerInfo: WorkerInfo,
        onClick: () -> Unit
    ) {
        Card(Modifier.padding(8.dp)){
            Row(
                modifier = Modifier.clickable(onClick = onClick).padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
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

    }

    @Composable
    fun WorkerHeaderDesc(workerInfo: WorkerInfo) {
        Card(Modifier.padding(8.dp)){
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape),
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

    }

    @Composable
    fun BookingDialog(workerInfo: WorkerInfo, viewModel: WorkerViewModel, onClose: () -> Unit) {
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
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            Text("Выбранное время: ${selectedTime.format(formatter)}")

            Button(onClick = {
                val service = AppointmentModel(
                    user_token = viewModel.userToken.value,
                    service_name = workerInfo.workerService,
                    performer_name = workerInfo.workerName,
                    performer_id = 5,
                    date = "${selectedDate.format(DateTimeFormatter.ISO_DATE)} ${selectedTime.format(formatter)}"
                )
                viewModel.bookService(service)
                onClose()
            }) {
                Text("Записаться")
            }
        }
    }

