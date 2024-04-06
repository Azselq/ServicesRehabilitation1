package com.example.servicesrehabilitation

import LoginScreen
import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.servicesrehabilitation.Scaffold.NavigationItem
import com.example.servicesrehabilitation.domain.UserProfile
import com.example.servicesrehabilitation.forumScreen.ForumScreen
import com.example.servicesrehabilitation.forumScreen.ForumViewModel
import com.example.servicesrehabilitation.navigation.AppNavGraph
import com.example.servicesrehabilitation.room.AppDatabase
import com.example.servicesrehabilitation.ui.theme.Light_blue
import com.example.servicesrehabilitation.ui.theme.Test
import com.example.servicesrehabilitation.workersScreen.WorkerScreen
import com.example.servicesrehabilitation.workersScreen.WorkerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    appDatabase: AppDatabase, navHost: NavHostController
){
    val navHostController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Light_blue
            ) {
                val navBackStackEntry by navHostController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favorite,
                    NavigationItem.Profile
                )
                items.forEachIndexed{index, item ->
                    NavigationBarItem(
                        selected = currentRoute == item.screen.route,
                        onClick = {navHostController.navigate(item.screen.route) },
                        icon = {
                            Icon( item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
                            indicatorColor = Test
                        )
                    )
                }
            }
        }){
        AppNavGraph(
            navHostController = navHostController ,
            serviceScreenContent = { ServiceScreen(navController = navHostController) },
            forumScreenContent = { ForumScreen(navController = navHostController) },
            profileScreenContent = { ProfileScreen(appDatabase, navHostController = navHost)},
        )

    }

}

