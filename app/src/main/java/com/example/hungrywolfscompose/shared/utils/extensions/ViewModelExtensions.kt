package com.example.hungrywolfscompose.shared.utils.extensions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hungrywolfscompose.shared.utils.Variables
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun ViewModel.performApiCall(
    showLoading: Boolean = true,
    block: suspend CoroutineScope.() -> Unit
) {
    if (showLoading) {
        Variables.loadingScreen.value = true
        viewModelScope.launch {
            block().also { delay(500) }
            Variables.loadingScreen.value = false
        }
    } else viewModelScope.launch { block() }
}