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