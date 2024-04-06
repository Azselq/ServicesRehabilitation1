package com.example.servicesrehabilitation.navigation

sealed class Screen(
    val route: String
) {
    object Service: Screen(ROUTE_SERVICE)
    object Forum: Screen(ROUTE_FORUM)
    object Profile: Screen(ROUTE_PROFILE)
    object Main: Screen(MAIN)
    object Login: Screen(LOGIN)
    object ForumDesc: Screen(FORUM_DESC)


    private companion object{
        const val ROUTE_SERVICE = "service"
        const val ROUTE_FORUM = "forum"
        const val ROUTE_PROFILE = "profile"
        const val MAIN = "main"
        const val LOGIN = "login"
        const val FORUM_DESC = "forumDesc"
    }

}