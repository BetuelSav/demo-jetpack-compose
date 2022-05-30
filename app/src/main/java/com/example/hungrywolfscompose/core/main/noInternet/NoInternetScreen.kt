package com.example.hungrywolfscompose.core.main.noInternet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hungrywolfscompose.R
import com.example.hungrywolfscompose.core.ui.theme.Gray42
import com.example.hungrywolfscompose.core.ui.theme.RedLight
import com.example.hungrywolfscompose.core.ui.theme.fontSfPro
import com.example.hungrywolfscompose.shared.base.BackPressHandler

@Composable
fun NoInternetScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        BackPressHandler(onBackPressed = {})

        Icon(
            painter = painterResource(id = R.drawable.ic_no_internet),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = stringResource(id = R.string.no_internet_title),
            fontFamily = fontSfPro,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = stringResource(id = R.string.no_internet_description),
            fontFamily = fontSfPro,
            fontSize = 17.sp,
            color = Gray42,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.padding(40.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(horizontal = 50.dp),
            shape = RoundedCornerShape(30.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = RedLight,
                contentColor = Color.White,
            )
        ) {
            Text(
                text = stringResource(id = R.string.no_internet_try_again),
                fontFamily = fontSfPro,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp
            )
        }
    }
}

@Preview
@Composable
fun NoInternetScreenPreview() {
    NoInternetScreen()
}