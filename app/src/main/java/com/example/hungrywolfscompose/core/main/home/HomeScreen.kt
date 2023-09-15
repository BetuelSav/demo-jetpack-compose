package com.example.hungrywolfscompose.core.main.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.hungrywolfscompose.R
import com.example.hungrywolfscompose.core.main.NavScreen
import com.example.hungrywolfscompose.core.ui.theme.GrayBright
import com.example.hungrywolfscompose.core.ui.theme.GrayDark
import com.example.hungrywolfscompose.core.ui.theme.GrayLightest
import com.example.hungrywolfscompose.core.ui.theme.RedLight
import com.example.hungrywolfscompose.core.ui.theme.fontSfPro
import com.example.hungrywolfscompose.core.ui.theme.fontSfProRounded
import com.example.hungrywolfscompose.shared.utils.extensions.noRippleClickable
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var selectedCategoryIndex by rememberSaveable { mutableStateOf(0) }
    val mealsPreviewListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ) {
        Title(Modifier.padding(start = 53.dp, top = 40.dp, end = 53.dp))
        SearchButton(
            onClick = { navController.navigate(NavScreen.SEARCH.route) },
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 53.dp, top = 16.dp, end = 53.dp, bottom = 45.dp)
        )
        LazyRow(contentPadding = PaddingValues(start = 53.dp, end = 27.dp)) {
            itemsIndexed(items = viewModel.mealCategories.value.categories) { index, item ->
                FoodCategoryItem(
                    category = item.category,
                    selected = index == selectedCategoryIndex,
                    onClick = {
                        selectedCategoryIndex = index
                        viewModel.getMealsFromCategory(item.category)
                        coroutineScope.launch {
                            mealsPreviewListState.animateScrollToItem(index = 0)
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.padding(40.dp))
        LazyRow(
            contentPadding = PaddingValues(start = 53.dp, bottom = 4.dp),
            state = mealsPreviewListState
        ) {
            items(items = viewModel.meals.value.meals) { item ->
                FoodCategoryPreviewItem(
                    imageUrl = item.mealImageUrl,
                    title = item.mealName,
                    onClick = { navController.navigate("${NavScreen.DETAILS.route}/${item.id}") }
                )
                Spacer(modifier = Modifier.width(27.dp))
            }
        }
    }
}

@Composable
fun Title(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.home_title),
        fontFamily = fontSfProRounded,
        fontWeight = FontWeight.Bold,
        fontSize = 42.sp
    )
}

@Composable
fun SearchButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 35.dp),
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = GrayLightest)
    ) {
        Icon(
            modifier = Modifier.padding(vertical = 21.dp),
            painter = painterResource(id = R.drawable.ic_loup),
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text(
            modifier = Modifier.weight(weight = 1f),
            text = stringResource(id = R.string.home_search),
            fontFamily = fontSfProRounded,
            fontWeight = FontWeight.SemiBold,
            fontSize = 17.sp,
            color = GrayDark
        )
    }
}

@Composable
fun FoodCategoryItem(category: String, selected: Boolean, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .noRippleClickable { onClick() }
    ) {
        Text(
            text = category,
            modifier = Modifier.padding(horizontal = 20.dp),
            fontFamily = fontSfPro,
            fontSize = 17.sp,
            color = if (selected) RedLight else GrayBright
        )
        Spacer(modifier = Modifier.height(10.dp))
        Card(
            content = {},
            shape = RoundedCornerShape(40.dp),
            backgroundColor = RedLight,
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .alpha(if (selected) 1f else 0f)
        )
    }
}

@Composable
fun FoodCategoryPreviewItem(imageUrl: String, title: String, onClick: () -> Unit) {
    Box(modifier = Modifier.noRippleClickable { onClick() }) {
        Card(
            modifier = Modifier
                .padding(top = 52.dp)
                .height(248.dp)
                .width(220.dp),
            backgroundColor = Color.White,
            shape = RoundedCornerShape(30.dp),
            content = {}
        )
        Column(
            modifier = Modifier.width(220.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                modifier = Modifier
                    .width(164.dp)
                    .height(164.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds,
                circularReveal = CircularReveal(duration = 250),
                imageModel = imageUrl
            )
            Text(
                text = title,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .width(164.dp),
                fontFamily = fontSfProRounded,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun TitlePreview() {
    Title()
}

@Preview
@Composable
fun SearchButtonPreview() {
    SearchButton(onClick = {})
}

@Preview
@Composable
fun FoodCategoryItemPreview() {
    FoodCategoryItem(category = "Beef", selected = true, onClick = {})
}