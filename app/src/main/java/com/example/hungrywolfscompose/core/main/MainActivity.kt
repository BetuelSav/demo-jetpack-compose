package com.example.hungrywolfscompose.core.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hungrywolfscompose.core.ui.theme.GrayLight
import com.example.hungrywolfscompose.core.ui.theme.HungryWolfsComposeTheme
import com.example.hungrywolfscompose.shared.base.LoadingScreen
import com.example.hungrywolfscompose.shared.utils.Variables

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HungryWolfsComposeTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val bottomBarVisible = when (navBackStackEntry?.destination?.route) {
                    NavScreen.DETAILS.route + "/{${NavArgs.MEAL_ID}}",
                    NavScreen.SEARCH.route, NavScreen.NO_INTERNET.route -> false
                    else -> true
                }

                var showLoadingScreen by remember { mutableStateOf(false) }
                Variables.loadingScreen.observe(this@MainActivity) { isVisible ->
                    showLoadingScreen = isVisible
                }

                Variables.isNetworkConnectedObservable.observe(this) { isNetworkConnected ->
                    when (isNetworkConnected) {
                        null -> return@observe
                        true -> if (currentDestination?.route == NavScreen.NO_INTERNET.route)
                            navController.popBackStack()
                        else -> if (currentDestination?.route != NavScreen.NO_INTERNET.route)
                            navController.navigate(NavScreen.NO_INTERNET.route)
                    }
                }

                Scaffold(
                    bottomBar = {
                        BottomBar(
                            navController = navController,
                            currentDestination = currentDestination,
                            visible = bottomBarVisible
                        )
                    },
                    backgroundColor = GrayLight
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())) {
                        NavHostGraph(navController = navController)
                        if (showLoadingScreen) LoadingScreen()
                    }
                }
            }
        }
    }
}
