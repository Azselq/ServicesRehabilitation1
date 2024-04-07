package com.example.servicesrehabilitation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.servicesrehabilitation.domain.AppointmentResponse
import com.example.servicesrehabilitation.navigation.Screen
import com.example.servicesrehabilitation.room.AppDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun ProfileScreen(
    appDatabase: AppDatabase,
    navHostController: NavHostController
) {
    val viewModelFactory = ProfileScreenViewModel.ProfileScreenViewModelFactory(appDatabase)
    val viewModel: ProfileScreenViewModel = viewModel(factory = viewModelFactory)
    val backGroundColor = colorResource(id = R.color.custom_light_blue)
    val user = viewModel.userInfo.observeAsState()
    val showDialog = remember { mutableStateOf(false) }
    viewModel.loadUserInfo()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backGroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = user.value?.image_url),
                contentDescription = "User Photo",
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 8.dp),
                contentScale = ContentScale.Crop
            )
            user.value?.let {
                Text(
                    text = it.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            user.value?.let {
                Text(
                    text = it.numberPhone,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
            user.value?.let {
                Text(
                    text = it.username,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
            Button(
                onClick = { showDialog.value = true },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Показать записи")
            }

            if (showDialog.value) {
                RecordsDialog(showDialog = showDialog, viewModel = viewModel)
            }

            Spacer(Modifier.weight(1f))
            Button(
                onClick = {
                    viewModel.deleteUserInfo()
                    navHostController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 65.dp),
            ) {
                Text("Выйти")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun RecordsDialog(showDialog: MutableState<Boolean>, viewModel: ProfileScreenViewModel) {
    val text = viewModel.appointmentInfo.collectAsState()
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text("Список записей") },
        text = {
            LazyColumn(
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 80.dp
                ),
                ) {
                items(
                    items = text.value,
                    key = { it.id }
                ) {
                    val dismissState = rememberDismissState()
                    if(dismissState.isDismissed(DismissDirection.EndToStart)){
                        viewModel.remove(it.id)
                    }
                    SwipeToDismiss(
                        modifier = Modifier.animateItemPlacement(),
                        state = dismissState,
                        background ={},
                        directions = setOf(DismissDirection.EndToStart),
                        dismissContent ={
                            AppointmentItem(appointment = it)
                        }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = { showDialog.value = false }) {
                Text("OK")
            }
        }
    )
}


@Composable
fun AppointmentItem(appointment: AppointmentResponse) {
    Row(
        modifier = Modifier.padding(4.dp).border(width = 2.dp, shape = RoundedCornerShape(8.dp), color = Color.Black).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f).padding(4.dp)
        ) {
            Text(text = appointment.performer_name)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = appointment.service_name)
        }
        Spacer(modifier = Modifier.width(10.dp))
        ParseTime(time = appointment.date)
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "null",

            )
    }
}
@Composable
fun ParseTime(time: String){
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val dateTime = LocalDateTime.parse(time, formatter)
    val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val formattedTime = dateTime.format(DateTimeFormatter.ofPattern("HH:mm"))

    Column(

    ) {
        Text(text = formattedDate)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = formattedTime)
    }

}
