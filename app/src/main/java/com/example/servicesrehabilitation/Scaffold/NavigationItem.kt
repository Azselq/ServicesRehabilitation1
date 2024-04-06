package com.example.servicesrehabilitation.Scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.servicesrehabilitation.R
import com.example.servicesrehabilitation.navigation.Screen

sealed class NavigationItem(
    val screen: Screen,
    val titleResId: Int,
    val icon: ImageVector
) {
    object Home:NavigationItem(
        screen = Screen.Service,
        titleResId = R.string.navigation_item_main,
        icon = Icons.Outlined.Home
    )
    object Favorite:NavigationItem(
        screen = Screen.Forum,
        titleResId = R.string.navigation_item_favorite,
        icon = Icons.Outlined.Favorite
    )
    object Profile:NavigationItem(
        screen = Screen.Profile,
        titleResId = R.string.navigation_item_profile,
        icon = Icons.Outlined.Person
    )
}