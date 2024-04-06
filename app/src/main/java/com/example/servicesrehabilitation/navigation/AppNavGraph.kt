package com.example.servicesrehabilitation.navigation

import LoginScreen
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.servicesrehabilitation.MainScreen
import com.example.servicesrehabilitation.forumScreen.ForumDetailScreen
import com.example.servicesrehabilitation.forumScreen.ForumViewModel
import com.example.servicesrehabilitation.workersScreen.RehabilitationItemDesc
import com.example.servicesrehabilitation.workersScreen.RehabilitationList
import com.example.servicesrehabilitation.workersScreen.WorkerItemDesc
import com.example.servicesrehabilitation.workersScreen.WorkerScreen

@Composable

fun AppNavGraph(
    navHostController: NavHostController,
    serviceScreenContent: @Composable () -> Unit,
    forumScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.Service.route,
    ) {
        composable(Screen.Service.route) {
            serviceScreenContent()
        }
        composable(Screen.Forum.route) {
            forumScreenContent()
        }
        composable(Screen.Profile.route) {
            profileScreenContent()
        }
        composable(
            "detail_screen/{postId}",
            arguments = listOf(navArgument("postId") { type = NavType.IntType })
        ) { backStackEntry ->
            ForumDetailScreen(navController = navHostController,postId = backStackEntry.arguments?.getInt("postId") ?: 0)
        }
        composable(
            "listWorksScreen/{serviceType}",
            arguments = listOf(navArgument("serviceType") { type = NavType.StringType })
        ){
            val serviceType = it.arguments?.getString("serviceType")
            WorkerScreen(serviceType = serviceType ?: "", navController = navHostController)
        }
        composable(
            "detail_worker_screen/{workerId}",
            arguments = listOf(navArgument("workerId") { type = NavType.IntType })
        ) { backStackEntry ->
            WorkerItemDesc(navController = navHostController,workerId = backStackEntry.arguments?.getInt("workerId") ?: 0)
        }
        composable("rehabilitation_list"){
            RehabilitationList(navController = navHostController)
        }
        composable(
            "rehabilitation_detail/{rehabilitationId}",
            arguments = listOf(navArgument("rehabilitationId") { type = NavType.IntType })
        ) { backStackEntry ->
            RehabilitationItemDesc(navController = navHostController, rehabilitationId = backStackEntry.arguments?.getInt("rehabilitationId") ?: 0)
        }
    }
}