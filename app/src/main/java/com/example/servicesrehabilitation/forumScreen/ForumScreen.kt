package com.example.servicesrehabilitation.forumScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.servicesrehabilitation.ProfileScreen
import com.example.servicesrehabilitation.R
import com.example.servicesrehabilitation.domain.ForumPost
import com.example.servicesrehabilitation.navigation.AppNavGraph
import com.example.servicesrehabilitation.navigation.Screen
import com.example.servicesrehabilitation.workersScreen.WorkerScreen
import com.example.servicesrehabilitation.workersScreen.WorkerViewModel

@Composable
fun ForumScreen(
    navController: NavController,
) {
    val backGroundColor = colorResource(id = R.color.custom_light_blue)
    val viewModel: ForumViewModel = viewModel()
    val forumPost = viewModel.forumPosts.collectAsState()
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
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = forumPost.value,
            key = { it.id }
        ) {
            ForumItem(forumPost = it) {
                println("Нажатие на пост с ID: ${it.id}")
                navController.navigate("detail_screen/${it.id}") {
                    launchSingleTop = true
                }
            }
        }

    }
}


