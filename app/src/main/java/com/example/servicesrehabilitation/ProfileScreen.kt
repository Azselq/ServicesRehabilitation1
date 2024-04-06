package com.example.servicesrehabilitation

import LoginScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.servicesrehabilitation.navigation.LoginNavGraph
import com.example.servicesrehabilitation.navigation.Screen
import com.example.servicesrehabilitation.room.AppDatabase



@Composable
fun ProfileScreen(appDatabase: AppDatabase,
                  navHostController: NavHostController
) {
    val viewModelFactory = ProfileScreenViewModel.ProfileScreenViewModelFactory(appDatabase)
    val viewModel: ProfileScreenViewModel = viewModel(factory = viewModelFactory)
    val backGroundColor = colorResource(id = R.color.custom_light_blue)
    val user = viewModel.userInfo.observeAsState()
    val showDialog = remember { mutableStateOf(false) }
    viewModel.loadUserInfo()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backGroundColor)) {
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
                navHostController.navigate(Screen.Login.route){
                    popUpTo(Screen.Login.route){
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
@Composable
fun RecordsDialog(showDialog: MutableState<Boolean>, viewModel: ProfileScreenViewModel) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text("Список записей") },
        text = {
               Text("tete")
        },
        confirmButton = {
            Button(onClick = { showDialog.value = false }) {
                Text("OK")
            }
        }
    )
}
