package com.example.hungrywolfscompose.core.main

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hungrywolfscompose.core.main.details.DetailsScreen
import com.example.hungrywolfscompose.core.main.favorite.FavoriteScreen
import com.example.hungrywolfscompose.core.main.home.HomeScreen
import com.example.hungrywolfscompose.core.main.profile.ProfileScreen
import com.example.hungrywolfscompose.core.main.search.SearchScreen

@Composable
fun NavHostGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavScreen.HOME.route
    ) {
        composable(route = BottomNavScreen.HOME.route) {
            HomeScreen(navController)
        }
        composable(route = BottomNavScreen.FAVORITE.route) {
            FavoriteScreen()
        }
        composable(route = BottomNavScreen.PROFILE.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = NavScreen.SEARCH.route) {
            SearchScreen(navController = navController)
        }
        composable(route = "${NavScreen.DETAILS.route}/{${NavArgs.MEAL_ID}}") { entry ->
            entry.arguments?.getString(NavArgs.MEAL_ID)?.let { mealId ->
                DetailsScreen(mealId = mealId, navController = navController)
            }
        }
    }
}