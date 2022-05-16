package com.example.hungrywolfscompose.core.main.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hungrywolfscompose.R
import com.example.hungrywolfscompose.core.ui.theme.Gray
import com.example.hungrywolfscompose.core.ui.theme.fontSfPro
import com.skydoves.landscapist.glide.GlideImage

private const val NAME = "Sav Betuel"
private const val EMAIL = "betuel@wolfpack-digital.com"
private const val PHONE = "+40 752 376 626"
private const val IMAGE_FILTER = "image/*"

@Composable
fun ProfileScreen() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { resultImageUri -> imageUri = resultImageUri }
    )

    Column(
        modifier = Modifier
            .padding(top = 60.dp, start = 48.dp, end = 48.dp)
            .fillMaxSize()
    ) {
        ProfileTitle()
        Spacer(modifier = Modifier.height(40.dp))
        PersonalDetailsCard(
            name = NAME,
            email = EMAIL,
            phone = PHONE,
            imageUri = imageUri,
            onImageClick = { selectImageLauncher.launch(IMAGE_FILTER) }
        )
        Spacer(modifier = Modifier.height(30.dp))
        ProfileOption(text = stringResource(id = R.string.profile_favorites))
        Spacer(modifier = Modifier.height(30.dp))
        ProfileOption(text = stringResource(id = R.string.terms_and_conditions))
    }
}

@Composable
fun ProfileTitle() {
    Text(
        text = stringResource(id = R.string.profile_title),
        fontFamily = fontSfPro,
        fontWeight = FontWeight.SemiBold,
        fontSize = 34.sp
    )
}

@Composable
fun PersonalDetailsCard(
    name: String,
    email: String,
    phone: String,
    imageUri: Uri?,
    onImageClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            GlideImage(
                modifier = Modifier
                    .size(90.dp, 100.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .clickable { onImageClick() },
                contentScale = ContentScale.FillBounds,
                imageModel = imageUri,
                error = painterResource(id = R.drawable.ic_profile_default)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Column {
                Text(
                    text = name,
                    fontFamily = fontSfPro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = email,
                    fontFamily = fontSfPro,
                    fontSize = 15.sp,
                    color = Gray,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Divider(
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp, end = 12.dp),
                    color = Gray
                )
                Text(
                    text = phone,
                    fontFamily = fontSfPro,
                    fontSize = 15.sp,
                    color = Gray
                )
            }
        }
    }
}

@Composable
fun ProfileOption(text: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White
    ) {
        Row(
            modifier = Modifier.padding(start = 23.dp, top = 20.dp, end = 36.dp, bottom = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                fontFamily = fontSfPro,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }
    }
}


@Preview
@Composable
fun PersonalDetailsCardPreview() {
    PersonalDetailsCard(
        name = NAME,
        email = EMAIL,
        phone = PHONE,
        imageUri = null,
        onImageClick = {}
    )
}

@Preview
@Composable
fun ProfileOptionPreview() {
    ProfileOption(text = "Favorites")
}