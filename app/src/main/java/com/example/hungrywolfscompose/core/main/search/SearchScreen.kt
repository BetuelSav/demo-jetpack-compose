package com.example.hungrywolfscompose.core.main.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.hungrywolfscompose.R
import com.example.hungrywolfscompose.core.main.NavScreen
import com.example.hungrywolfscompose.core.main.details.BackButton
import com.example.hungrywolfscompose.core.ui.theme.Gray
import com.example.hungrywolfscompose.core.ui.theme.Gray42
import com.example.hungrywolfscompose.core.ui.theme.Gray93
import com.example.hungrywolfscompose.core.ui.theme.Gray98
import com.example.hungrywolfscompose.core.ui.theme.GrayBright
import com.example.hungrywolfscompose.core.ui.theme.fontSfPro
import com.example.hungrywolfscompose.core.ui.theme.fontSfProRounded
import com.example.hungrywolfscompose.data.models.MealDetail
import com.example.hungrywolfscompose.shared.utils.extensions.noRippleClickable
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.getViewModel

@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchViewModel = getViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Gray93)
    ) {
        SearchAndGoBack(
            modifier = Modifier.padding(start = 12.dp, top = 40.dp, bottom = 20.dp),
            onBackClicked = { navController.popBackStack() },
            onQueryChange = viewModel::getMealsForQuery
        )
        viewModel.meals.value.mealDetails?.let {
            Results(
                meals = it,
                onMealClick = { mealId ->
                    navController.navigate("${NavScreen.DETAILS.route}/$mealId")
                }
            )
        } ?: NoResults()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchAndGoBack(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onQueryChange: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    val keyboard = LocalSoftwareKeyboardController.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackButton(onClick = onBackClicked)
        TextField(
            value = query,
            onValueChange = { newValue ->
                query = newValue
                onQueryChange(newValue)
            },
            singleLine = true,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.search_placeholder),
                    fontFamily = fontSfProRounded,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Gray,
                    fontSize = 17.sp
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                backgroundColor = Color.Transparent,
                cursorColor = Color.Black,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                placeholderColor = GrayBright
            ),
            textStyle = TextStyle(
                fontFamily = fontSfProRounded,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                fontSize = 17.sp
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { keyboard?.hide() })
        )
    }
}

@Composable
fun Results(
    meals: List<MealDetail>,
    onMealClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(
                color = Gray98,
                shape = RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 30.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                )
            )
    ) {
        Text(
            text = stringResource(id = R.string.search_found_results, meals.size),
            textAlign = TextAlign.Center,
            fontFamily = fontSfProRounded,
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, top = 12.dp, end = 32.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 156.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(35.dp),
            horizontalArrangement = Arrangement.spacedBy(35.dp)
        ) {
            items(meals) { item ->
                MealItem(
                    image = item.imageUrl,
                    title = item.name,
                    modifier = Modifier.noRippleClickable { onMealClick(item.id) }
                )
            }
        }
    }
}

@Composable
fun NoResults() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_loup),
            contentDescription = null,
            tint = Gray,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.padding(24.dp))
        Text(
            text = stringResource(id = R.string.search_not_found_title),
            fontFamily = fontSfPro,
            fontWeight = FontWeight.SemiBold,
            fontSize = 28.sp,
            color = Color.Black
        )
        Spacer(modifier = Modifier.padding(12.dp))
        Text(
            text = stringResource(id = R.string.search_not_found_description),
            fontFamily = fontSfPro,
            fontSize = 17.sp,
            color = Gray42,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun MealItem(
    modifier: Modifier = Modifier,
    image: String,
    title: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = modifier.background(color = Color.White, shape = RoundedCornerShape(30.dp))
    ) {
        GlideImage(
            imageModel = image,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(10.dp)
                .size(128.dp)
                .clip(shape = CircleShape)
        )
        Text(
            text = title,
            fontFamily = fontSfProRounded,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            color = Color.Black,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun SearchAndGoBackPreview() {
    SearchAndGoBack(onBackClicked = {}, onQueryChange = {})
}