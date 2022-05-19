package com.example.hungrywolfscompose.core.main.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import org.koin.androidx.compose.getViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel = getViewModel()) {
    Text(
        text = "Home Screen",
        textAlign = TextAlign.Center
    )
}