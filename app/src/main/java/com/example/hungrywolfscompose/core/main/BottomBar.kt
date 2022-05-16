package com.example.hungrywolfscompose.core.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.hungrywolfscompose.R
import com.example.hungrywolfscompose.core.ui.theme.Gray
import com.example.hungrywolfscompose.core.ui.theme.GrayLight
import com.example.hungrywolfscompose.core.ui.theme.RedLight

@Composable
fun BottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        BottomNavigation(
            backgroundColor = GrayLight,
            elevation = 0.dp
        ) {
            BottomNavScreen.values().forEach { screen ->
                BottomNavigationItem(
                    icon = { Icon(painterResource(screen.icon), null) },
                    selected = currentDestination?.route == screen.route,
                    selectedContentColor = RedLight,
                    unselectedContentColor = Gray,
                    onClick = {
                        navController.navigate(
                            route = screen.route,
                            builder = {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        )
                    },
                )
            }
        }
    }
}