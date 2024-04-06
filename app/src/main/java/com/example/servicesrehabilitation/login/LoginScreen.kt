import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.servicesrehabilitation.forumScreen.ForumViewModel
import com.example.servicesrehabilitation.MainScreen
import com.example.servicesrehabilitation.R
import com.example.servicesrehabilitation.domain.UserProfile
import com.example.servicesrehabilitation.login.LoginViewModel
import com.example.servicesrehabilitation.login.LoginViewModelFactory
import com.example.servicesrehabilitation.navigation.LoginNavGraph
import com.example.servicesrehabilitation.navigation.Screen
import com.example.servicesrehabilitation.room.AppDatabase
import com.example.servicesrehabilitation.ui.theme.Test
import com.example.servicesrehabilitation.workersScreen.WorkerViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(appDatabase: AppDatabase) {
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    val backGroundColor = colorResource(id = R.color.custom_light_blue)
    val viewModelFactory = LoginViewModelFactory(appDatabase)
    val viewModel: LoginViewModel = viewModel(factory = viewModelFactory)
    val loginState by viewModel.loginState.collectAsState()
    val navHostController = rememberNavController()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backGroundColor)
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Services&Rehabilitation",
                fontSize = 24.sp,
                color = colorResource(id = R.color.test),
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                //fontFamily = FontFamily.Cursive
            )
            // Логотип
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Logo",
                modifier = Modifier.size(256.dp)
            )

            // Приветствие
            Text(
                text = "Добро пожаловать",
                fontSize = 24.sp,
                color = colorResource(id = R.color.test),
                fontWeight = FontWeight.Bold
            )

            val customColor = colorResource(id = R.color.custom_light_blue)
            val cardColors = CardDefaults.cardColors(
                containerColor = customColor
            )
            Spacer(modifier = Modifier.height(16.dp))
            Card(
                modifier = Modifier.padding(8.dp),
                shape = RoundedCornerShape(8.dp),
                colors = cardColors
            ){
                // Поля ввода
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Почта") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Пароль") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.loginUser(email.value, password.value)
                        /*coroutineScope.launch{
                            viewModel.getAllUser()
                        */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Test)
                ) {
                    Text("Войти")

                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            when (loginState) {
                is LoginViewModel.LoginState.Loading -> {
                    CircularProgressIndicator()
                }
                is LoginViewModel.LoginState.Success -> {
                    LaunchedEffect(Unit) {
                        navHostController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                    Text("Login Success")
                }
                is LoginViewModel.LoginState.AlreadyLoggedIn -> {
                    LaunchedEffect(Unit) {
                        navHostController.navigate(Screen.Main.route) {
                            popUpTo(Screen.Login.route) {
                                inclusive = true
                            }
                        }
                    }
                }
                is LoginViewModel.LoginState.Error -> {
                    Text("Login Failed: ${(loginState as LoginViewModel.LoginState.Error).message}")
                }
                else -> Unit
            }
            TextButton(onClick = { /* TODO: Обработка нажатия */ }) {
                Text("Еще нет аккаунта? Зарегистрироваться")
            }
        }
        LoginNavGraph(navHostController = navHostController, mainScreenContent = { MainScreen(
            appDatabase = appDatabase, navHost = navHostController
        )}) {
        }
    }

}

