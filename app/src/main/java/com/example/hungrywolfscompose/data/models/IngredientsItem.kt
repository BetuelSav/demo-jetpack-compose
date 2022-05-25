package com.example.hungrywolfscompose.data.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class IngredientsItem(
    val measurement: String,
    val ingredient: String,
    val initialChecked: Boolean = false
) {
    var checked by mutableStateOf(initialChecked)
}
