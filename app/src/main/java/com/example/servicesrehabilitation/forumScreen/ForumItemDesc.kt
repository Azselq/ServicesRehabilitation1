package com.example.servicesrehabilitation.forumScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.servicesrehabilitation.domain.ForumPost
import com.example.servicesrehabilitation.domain.WorkerInfo



@Composable
fun ForumItem(
    forumPost: ForumPost,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier.padding(8.dp).clickable(onClick = onClick)
    ) {
        Column(
            Modifier.padding(8.dp)
        ) {
            PostHeaderDesc(forumPost)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = forumPost.titleText,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
@Composable
fun PostHeaderDesc(
    forumPost: ForumPost
){
    Row (
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            painter = rememberAsyncImagePainter(model = forumPost.avatarResId),
            contentDescription = "null"
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = forumPost.userName)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = forumPost.publicationTime)
        }

        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "null",

            )
    }
}
