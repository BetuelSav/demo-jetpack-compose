package com.example.hungrywolfscompose.shared.base

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.hungrywolfscompose.core.ui.theme.RedLight

@Composable
fun LoadingScreen() {
    Dialog(
        onDismissRequest = { },
        DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {
            CircularProgressIndicator(
                color = RedLight,
                strokeWidth = 5.dp,
                modifier = Modifier.size(150.dp)
            )
    }
}