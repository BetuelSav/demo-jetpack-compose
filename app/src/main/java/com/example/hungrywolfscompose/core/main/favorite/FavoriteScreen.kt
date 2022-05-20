package com.example.hungrywolfscompose.core.main.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hungrywolfscompose.core.ui.theme.fontSfProRounded

@Composable
fun FavoriteScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Favorite Screen",
            fontFamily = fontSfProRounded,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp
        )
    }
}

@Preview
@Composable
fun FavoriteScreenPreview() {
    FavoriteScreen()
}