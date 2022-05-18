package com.example.hungrywolfscompose.core.main.profile

import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.hungrywolfscompose.R
import com.example.hungrywolfscompose.core.ui.theme.Gray
import com.example.hungrywolfscompose.core.ui.theme.GrayLight
import com.example.hungrywolfscompose.core.ui.theme.fontSfPro
import com.example.hungrywolfscompose.core.ui.theme.fontSfProRounded
import com.example.hungrywolfscompose.shared.utils.Constants
import com.example.hungrywolfscompose.shared.utils.ImageUtil
import com.example.hungrywolfscompose.shared.utils.extensions.noRippleClickable
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen() {
    val coroutineScope = rememberCoroutineScope()
    val modalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    val imageUtil = ImageUtil(LocalContext.current, Constants.PROFILE_IMAGE)
    var imageBitmap by remember { mutableStateOf(imageUtil.loadPhotoFromInternalStorage()) }

    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            it?.let { uri ->
                imageUtil.getBitmapFromUri(uri)?.let { bitmap ->
                    imageBitmap = bitmap
                    imageUtil.savePhotoToInternalStorage(bitmap)
                }
            }
        }
    )
    val takeImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = {
            it?.let { bitmap ->
                imageBitmap = bitmap
                imageUtil.savePhotoToInternalStorage(bitmap)
            }
        }
    )

    ModalBottomSheetLayout(
        sheetBackgroundColor = GrayLight,
        sheetElevation = 0.dp,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        sheetState = modalState,
        sheetContent = {
            BottomSheetPhotoPikerContent(
                takePhotoClicked = {
                    coroutineScope.launch {
                        modalState.hide()
                        takeImageLauncher.launch()
                    }
                },
                uploadPhotoClicked = {
                    coroutineScope.launch {
                        modalState.hide()
                        selectImageLauncher.launch(Constants.IMAGE_FILTER)
                    }
                }
            )
        }
    ) {
        ProfileScreenContent(
            profileImageBitmap = imageBitmap,
            profileImageClicked = { coroutineScope.launch { modalState.show() } }
        )
    }
}

@Composable
fun ProfileScreenContent(
    profileImageBitmap: Bitmap?,
    profileImageClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(top = 60.dp, start = 48.dp, end = 48.dp)
            .fillMaxSize()
    ) {
        ProfileTitle()
        Spacer(modifier = Modifier.height(40.dp))
        PersonalDetailsCard(
            name = Constants.NAME,
            email = Constants.EMAIL,
            phone = Constants.PHONE,
            imageBitmap = profileImageBitmap,
            onImageClick = profileImageClicked
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
    imageBitmap: Bitmap?,
    onImageClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        backgroundColor = Color.White
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            GlideImage(
                contentScale = ContentScale.Crop,
                imageModel = imageBitmap,
                error = painterResource(id = R.drawable.ic_profile_default),
                modifier = Modifier
                    .size(90.dp, 100.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .clickable { onImageClick() }
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

@Composable
fun BottomSheetPhotoPikerContent(
    takePhotoClicked: () -> Unit,
    uploadPhotoClicked: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(id = R.string.profile_modal_title),
            fontFamily = fontSfProRounded,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_take_photo),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .noRippleClickable { takePhotoClicked() }
            )
            Image(
                painter = painterResource(id = R.drawable.ic_upload_photo),
                contentDescription = null,
                modifier = Modifier
                    .size(height = 192.dp, width = 200.dp)
                    .noRippleClickable { uploadPhotoClicked() }
            )
        }
    }
}


@Preview
@Composable
fun PersonalDetailsCardPreview() {
    PersonalDetailsCard(
        name = Constants.NAME,
        email = Constants.EMAIL,
        phone = Constants.PHONE,
        imageBitmap = null,
        onImageClick = {}
    )
}

@Preview
@Composable
fun ProfileOptionPreview() {
    ProfileOption(text = "Favorites")
}