package com.example.servicesrehabilitation.forumScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.servicesrehabilitation.R
import com.example.servicesrehabilitation.domain.ForumPost
import com.example.servicesrehabilitation.workersScreen.LoadingState

@Composable
fun ForumDetailScreen(navController: NavController, postId: Int) {
    val viewModel: ForumViewModel = viewModel()
    val forumPost = viewModel.forumPosts.collectAsState()
    val post = viewModel.forumPost.collectAsState()
    val loadingState = viewModel.loadingState.collectAsState()
    Log.d("oliClown", "$postId")
    when (loadingState.value) {
        LoadingState.LOADING -> {
            CircularProgressIndicator()
        }
        LoadingState.SUCCESS -> {
            viewModel.getForumPost(postId)
            ForumDetail(post.value)
        }
        LoadingState.ERROR -> {
            Text("Произошла ошибка при загрузке данных.")
        }
    }
}


@Composable
fun ForumDetail(forumPost: ForumPost){
    val backGroundColor = colorResource(id = R.color.custom_light_blue)
    Box(modifier = Modifier
        .fillMaxSize()
        .background(backGroundColor)){
        Card(
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Row (
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if (forumPost != null) {
                        Image(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(CircleShape),
                            painter = rememberAsyncImagePainter(model = forumPost.avatarResId),
                            contentDescription = "null"
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        if (forumPost != null) {
                            Text(text = forumPost.userName)
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "14:00")
                    }

                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = "null",

                        )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (forumPost != null) {
                    Text(
                        text = forumPost.titleText,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (forumPost != null) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        painter = rememberAsyncImagePainter(model = forumPost.contentImageResId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                if (forumPost != null) {
                    Text(text = forumPost.contentText)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

}