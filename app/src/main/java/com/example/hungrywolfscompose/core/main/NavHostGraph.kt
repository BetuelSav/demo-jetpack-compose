package com.example.hungrywolfscompose.core.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hungrywolfscompose.core.main.favorite.FavoriteScreen
import com.example.hungrywolfscompose.core.main.home.HomeScreen
import com.example.hungrywolfscompose.core.main.profile.ProfileScreen

@Composable
fun NavHostGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.HOME.route
    ) {
        composable(route = BottomNavScreen.HOME.route) {
            HomeScreen()
        }
        composable(route = BottomNavScreen.FAVORITE.route) {
            FavoriteScreen()
        }
        composable(route = BottomNavScreen.PROFILE.route) {
            ProfileScreen()
        }
    }
}