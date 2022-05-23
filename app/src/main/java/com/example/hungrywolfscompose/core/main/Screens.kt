package com.example.hungrywolfscompose.core.main

import com.example.hungrywolfscompose.R

enum class BottomNavScreen(val route: String, val icon: Int) {
    HOME(
        route = "home",
        icon = R.drawable.ic_home
    ),
    FAVORITE(
        route = "favorite",
        icon = R.drawable.ic_favorite
    ),
    PROFILE(
        route = "profile",
        icon = R.drawable.ic_profile
    )
}

enum class NavScreen(val route: String) {
    DETAILS(route = "details"),
    SEARCH(route = "search")
}

object NavArgs {
    const val MEAL_ID = "meal_id"
}