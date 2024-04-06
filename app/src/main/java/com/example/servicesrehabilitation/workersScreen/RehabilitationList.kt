package com.example.servicesrehabilitation.workersScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.servicesrehabilitation.R
import com.example.servicesrehabilitation.domain.RehabilitationInfo
import com.example.servicesrehabilitation.ui.theme.Test

@Composable
fun RehabilitationItem(
    rehabilitationInfo: RehabilitationInfo,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(4.dp)
            .height(40.dp)
            .border(width = 2.dp, shape = RoundedCornerShape(8.dp), color = Color.Black)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rehabilitationInfo.titleText,
            modifier = Modifier.padding(4.dp),
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RehabilitationItemDesc(navController: NavController, rehabilitationId: Int) {
    val viewModel: RehabilitationScreenViewModel = viewModel()
    val rehabilitationInfo = viewModel.rehabilitationInfo.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()

    Card(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.White)
    ) {
        when (loadingState.value) {
            LoadingState.LOADING -> {
                CircularProgressIndicator()
            }

            LoadingState.SUCCESS -> {
                Column(
                    modifier = Modifier.background(Color.White)
                ) {
                    val rehabilitation = rehabilitationInfo.value[rehabilitationId-1]
                    Text(
                        text = rehabilitation.titleText,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Image(
                        modifier = Modifier.size(height = 200.dp,width = 500.dp),
                        painter = rememberAsyncImagePainter(model = rehabilitation.contentImageResId),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = rehabilitation.contentText)
                }
            }

            LoadingState.ERROR -> {
                Text("Произошла ошибка при загрузке данных.")
            }
        }

    }
}

@Composable
fun RehabilitationList(
    navController: NavController,
) {
    val viewModel: RehabilitationScreenViewModel = viewModel()
    val rehabilitation = viewModel.rehabilitationInfo.collectAsState()
    LazyColumn(
        contentPadding = PaddingValues(
            top = 16.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 80.dp
        ),

        ) {
        items(
            items = rehabilitation.value,
            key = { it.id }
        ) {
            RehabilitationItem(rehabilitationInfo = it) {
                navController.navigate("rehabilitation_detail/${it.id}")
            }
        }
    }
}