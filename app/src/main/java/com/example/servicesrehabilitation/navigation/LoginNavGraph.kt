package com.example.servicesrehabilitation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.servicesrehabilitation.MainScreen

@Composable
fun LoginNavGraph(
    navHostController: NavHostController,
    mainScreenContent: @Composable () -> Unit,
    loginScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Login.route,
    ) {
        composable(Screen.Main.route) {
            mainScreenContent()
        }
        composable(Screen.Login.route) {
            loginScreenContent()
        }
    }
}