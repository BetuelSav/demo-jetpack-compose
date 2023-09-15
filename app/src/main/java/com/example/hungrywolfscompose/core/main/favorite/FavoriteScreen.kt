package com.example.hungrywolfscompose.core.main.favorite

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.SwipeableState
import androidx.compose.material.Text
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.hungrywolfscompose.R
import com.example.hungrywolfscompose.core.main.NavScreen
import com.example.hungrywolfscompose.core.ui.theme.fontSfPro
import com.example.hungrywolfscompose.core.ui.theme.fontSfProRounded
import com.example.hungrywolfscompose.shared.utils.extensions.noRippleClickable
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteScreen(
    navController: NavHostController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    viewModel.updateFavoriteMealList()
    val offsetXinDp = (-80).dp
    val offsetXinPx = with(LocalDensity.current) { offsetXinDp.toPx() }
    val anchors = mapOf(0f to 0, offsetXinPx to 1)

    LazyColumn {
        item {
            Spacer(modifier = Modifier.size(45.dp))
            Title()
            Spacer(modifier = Modifier.size(45.dp))
            SwipeToDeleteText()
            Spacer(modifier = Modifier.size(20.dp))
        }
        viewModel.meals.value?.let { meals ->
            itemsIndexed(
                items = meals,
                key = { _, meal -> meal.id }
            ) { index, item ->
                MealCardWithDelete(
                    imagesUrl = item.imageUrl,
                    name = item.name,
                    anchor = anchors,
                    onDeleteClicked = { viewModel.deleteMeal(index = index) },
                    onCardClick = { navController.navigate("${NavScreen.DETAILS.route}/${item.id}") },
                    modifier = Modifier
                        .padding(horizontal = 50.dp, vertical = 10.dp)
                        .animateItemPlacement(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessVeryLow
                            )
                        )
                )
            }
        }
    }

}

@Composable
fun Title() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.favorite_title),
        textAlign = TextAlign.Center,
        fontFamily = fontSfPro,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    )
}

@Composable
fun SwipeToDeleteText() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = R.drawable.ic_swipe), contentDescription = null)
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = stringResource(id = R.string.favorite_swipe_to_delete),
            fontFamily = fontSfPro,
            fontSize = 10.sp
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MealCardWithDelete(
    modifier: Modifier = Modifier,
    imagesUrl: String?,
    name: String?,
    anchor: Map<Float, Int>,
    onCardClick: () -> Unit,
    onDeleteClicked: () -> Unit
) {
    val swipeState = rememberSwipeableState(initialValue = 0)
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = modifier) {
        DeleteCard {
            coroutineScope.launch { swipeState.snapTo(0) }
            onDeleteClicked()
        }
        MealCard(
            imagesUrl = imagesUrl,
            name = name,
            anchor = anchor,
            swipeState = swipeState,
            onCardClick = onCardClick
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MealCard(
    imagesUrl: String?,
    name: String?,
    anchor: Map<Float, Int>,
    swipeState: SwipeableState<Int>,
    onCardClick: () -> Unit
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
        .swipeable(
            state = swipeState,
            anchors = anchor,
            orientation = Orientation.Horizontal,
            thresholds = { _, _ ->
                FractionalThreshold(0.3f)
            }
        ),
        backgroundColor = Color.White,
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .clickable(enabled = swipeState.offset.value.roundToInt() == 0) { onCardClick() }
                .padding(start = 16.dp, top = 16.dp, end = 40.dp, bottom = 16.dp)
        ) {
            GlideImage(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds,
                imageModel = imagesUrl,
            )
            Spacer(modifier = Modifier.padding(14.dp))
            Text(
                text = name ?: "",
                fontFamily = fontSfProRounded,
                fontWeight = FontWeight.SemiBold,
                fontSize = 17.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DeleteCard(onDeleteClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(end = 2.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            modifier = Modifier.noRippleClickable { onDeleteClicked() },
            painter = painterResource(id = R.drawable.ic_delete),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@Preview
@Composable
fun DeleteCardPreview() {
    DeleteCard {}
}