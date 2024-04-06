package com.example.servicesrehabilitation

import LoginScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.servicesrehabilitation.domain.UserProfile
import com.example.servicesrehabilitation.forumScreen.ForumScreen
import com.example.servicesrehabilitation.forumScreen.ForumViewModel
import com.example.servicesrehabilitation.login.LoginViewModel
import com.example.servicesrehabilitation.login.LoginViewModelFactory
import com.example.servicesrehabilitation.navigation.AppNavGraph
import com.example.servicesrehabilitation.room.AppDatabase
import com.example.servicesrehabilitation.ui.theme.ServicesRehabilitationTheme
import com.example.servicesrehabilitation.workersScreen.WorkerScreen
import com.example.servicesrehabilitation.workersScreen.WorkerViewModel

class MainActivity : ComponentActivity() {
    private val db by lazy { AppDatabase.getDatabase(this) }
    private val loginViewModel by viewModels<LoginViewModel> {
        LoginViewModelFactory(db)
    }
    private val viewModel by viewModels<ForumViewModel>()
    private val workerViewModel by viewModels<WorkerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?)

    {
        super.onCreate(savedInstanceState)
        setContent {
            ServicesRehabilitationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                        LoginScreen(db)
                    //MainScreen(viewModel =viewModel, userProfile = UserProfile(), workerViewModel = workerViewModel)
                }
            }
        }
    }

}
