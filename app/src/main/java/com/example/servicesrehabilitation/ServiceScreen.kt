package com.example.servicesrehabilitation
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.servicesrehabilitation.R
import com.example.servicesrehabilitation.room.AppDatabase
import com.example.servicesrehabilitation.workersScreen.WorkerViewModel

@Composable
fun ServiceScreen(navController: NavController,appDatabase: AppDatabase) {
    val backGroundColor = colorResource(id = R.color.custom_light_blue)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backGroundColor)
            .padding(8.dp)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Услуги",
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                color = colorResource(id = R.color.test)
            )
            Spacer(modifier = Modifier.height(100.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ServiceItem(navController = navController,iconId = R.drawable.grooming, text = "Груминг", serviceType = "grooming")
                ServiceItem(navController = navController,iconId = R.drawable.walking, text = "Выгул",serviceType = "walking")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                ServiceItem(navController = navController,iconId = R.drawable.training, text = "Дрессировка",serviceType = "training",)
                ServiceItem(navController = navController,iconId = R.drawable.rehabilitation, text = "Реабилитация",serviceType = "rehabilitation", )
            }
        }
    }

}

@Composable
fun ServiceItem(navController: NavController,iconId: Int, text: String, serviceType: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            if(serviceType != "rehabilitation" ){
                println("Нажатие на пост с ID: ${serviceType}")
                navController.navigate("listWorksScreen/$serviceType")
            }else{
                println("Нажатие на пост с ID: ${serviceType}")
                navController.navigate("rehabilitation_list")
            }
        }
    ) {
        Image(painter = painterResource(id = iconId), contentDescription = text, Modifier.size(150.dp))
        Text(text = text)
    }
}
