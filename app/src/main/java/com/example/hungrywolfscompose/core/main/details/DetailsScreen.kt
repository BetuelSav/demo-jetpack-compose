package com.example.hungrywolfscompose.core.main.details

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun DetailsScreen(
    mealId: String,
    navController: NavHostController,
    viewModel: DetailsViewModel = getViewModel() { parametersOf(mealId) }
) {
    Text(text = "Details Screen\n meal id: $mealId")
}