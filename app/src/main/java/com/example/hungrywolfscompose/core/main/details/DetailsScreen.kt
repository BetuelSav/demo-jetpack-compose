package com.example.hungrywolfscompose.core.main.details

import android.content.res.Configuration
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import com.example.hungrywolfscompose.R
import com.example.hungrywolfscompose.core.ui.theme.GrayBright
import com.example.hungrywolfscompose.core.ui.theme.GrayDark
import com.example.hungrywolfscompose.core.ui.theme.RedLight
import com.example.hungrywolfscompose.core.ui.theme.fontSfPro
import com.example.hungrywolfscompose.core.ui.theme.fontSfProRounded
import com.example.hungrywolfscompose.shared.utils.extensions.noRippleClickable
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

private const val ID_BACK = "id_back"
private const val ID_FAVORITE_TOGGLE = "id_favorite_toggle"
private const val ID_PAGER_WITH_INDICATOR = "id_pager_with_indicator"
private const val ID_TITLE = "id_title"
private const val ID_TAGS = "id_tags"
private const val ID_SUBTITLE_INGREDIENTS = "id_subtitle"
private const val SHRINK_SIZE = 4

@Composable
fun DetailsScreen(
    mealId: String,
    navController: NavHostController,
    viewModel: DetailsViewModel = getViewModel() { parametersOf(mealId) }
) {
    var ingredientsExpanded by remember { mutableStateOf(false) }
    var instructionsExpanded by remember { mutableStateOf(false) }

    val constraintsForPortraitMode = ConstraintSet {
        val backButton = createRefFor(ID_BACK)
        val favoriteToggle = createRefFor(ID_FAVORITE_TOGGLE)
        val pagerWithIndicator = createRefFor(ID_PAGER_WITH_INDICATOR)
        val title = createRefFor(ID_TITLE)
        val tags = createRefFor(ID_TAGS)
        val subtitleIngredients = createRefFor(ID_SUBTITLE_INGREDIENTS)

        constrain(backButton) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
        constrain(favoriteToggle) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        constrain(pagerWithIndicator) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(backButton.bottom, 10.dp)
            width = Dimension.matchParent
            height = Dimension.wrapContent
        }
        constrain(title) {
            top.linkTo(pagerWithIndicator.bottom, 16.dp)
            width = Dimension.matchParent
        }
        constrain(tags) {
            top.linkTo(title.bottom, 4.dp)
            width = Dimension.matchParent
        }
        constrain(subtitleIngredients) {
            top.linkTo(tags.bottom, 16.dp)
            width = Dimension.matchParent
        }
    }
    val constraintsForLandscapeMode = ConstraintSet {
        val backButton = createRefFor(ID_BACK)
        val favoriteToggle = createRefFor(ID_FAVORITE_TOGGLE)
        val pagerWithIndicator = createRefFor(ID_PAGER_WITH_INDICATOR)
        val title = createRefFor(ID_TITLE)
        val tags = createRefFor(ID_TAGS)
        val subtitleIngredients = createRefFor(ID_SUBTITLE_INGREDIENTS)

        val startGuideline = createGuidelineFromStart(32.dp)
        val endGuideline = createGuidelineFromEnd(32.dp)
        val barrierBottomTitle = createBottomBarrier(title, pagerWithIndicator)

        constrain(favoriteToggle) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
        }
        constrain(backButton) {
            top.linkTo(parent.top)
            end.linkTo(parent.end)
        }
        constrain(title) {
            top.linkTo(backButton.bottom, 16.dp)
            start.linkTo(startGuideline)
            end.linkTo(pagerWithIndicator.start)
            width = Dimension.preferredWrapContent
        }
        constrain(pagerWithIndicator) {
            top.linkTo(title.top, 10.dp)
            bottom.linkTo(title.bottom)
            start.linkTo(title.end)
            end.linkTo(endGuideline)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(tags) {
            top.linkTo(barrierBottomTitle, 4.dp)
            width = Dimension.matchParent
        }
        constrain(subtitleIngredients) {
            top.linkTo(tags.bottom, 16.dp)
            width = Dimension.matchParent
        }
        createHorizontalChain(favoriteToggle, backButton, chainStyle = ChainStyle.Spread)
    }
    val currentScreenOrientation = LocalContext.current.resources.configuration.orientation
    val constraints =
        if (currentScreenOrientation == Configuration.ORIENTATION_PORTRAIT) constraintsForPortraitMode
        else constraintsForLandscapeMode

    LazyColumn(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        item {
            ConstraintLayout(constraintSet = constraints) {
                BackButton(
                    modifier = Modifier
                        .padding(start = 12.dp, top = 22.dp)
                        .layoutId(ID_BACK),
                    onClick = { navController.popBackStack() }
                )
                ToggleFavoriteButton(
                    mealAddedToFavorite = viewModel.isMealFavorite.value,
                    modifier = Modifier
                        .padding(top = 22.dp, end = 12.dp)
                        .layoutId(ID_FAVORITE_TOGGLE),
                    onClick = viewModel::toggleFavoriteMeal
                )

                PagerWithIndicator(
                    modifier = Modifier.layoutId(ID_PAGER_WITH_INDICATOR),
                    foodUrl = viewModel.mealDetails.value?.imageUrl,
                    videoLink = viewModel.mealDetails.value?.youtubeLink
                )
                Text(
                    text = viewModel.mealDetails.value?.name ?: "",
                    fontFamily = fontSfProRounded,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .layoutId(ID_TITLE)
                )
                FlowRow(
                    mainAxisSpacing = 10.dp,
                    crossAxisSpacing = 10.dp,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .layoutId(ID_TAGS)
                ) {
                    viewModel.getMealTags()?.forEach { tag ->
                        MealTag(tag = tag)
                    }
                }
                Text(
                    text = stringResource(id = R.string.details_ingredients_title),
                    fontFamily = fontSfProRounded,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .layoutId(ID_SUBTITLE_INGREDIENTS)
                )
            }
        }

        viewModel.ingredientsList.value?.let { ingredientsList ->
            itemsIndexed(
                items = ingredientsList.take(if (ingredientsExpanded) ingredientsList.size else SHRINK_SIZE),
            ) { index, item ->
                Ingredients(
                    modifier = Modifier.padding(start = 40.dp),
                    measurement = item.measurement,
                    ingredient = item.ingredient,
                    checked = item.checked,
                    onChecked = { checked -> viewModel.ingredientChecked(checked, index) }
                )
            }
            if (ingredientsList.size > SHRINK_SIZE)
                item {
                    ExpandShrinkButton(
                        modifier = Modifier.padding(start = 40.dp),
                        expanded = ingredientsExpanded,
                        onClick = { ingredientsExpanded = !ingredientsExpanded }
                    )
                }
        }
        item {
            ConstraintLayout {
                val (instructionsTitle, instructions, expandShrinkButton) = createRefs()
                Text(
                    text = stringResource(id = R.string.details_instructions_title),
                    fontFamily = fontSfProRounded,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 17.sp,
                    modifier = Modifier
                        .padding(start = 32.dp, top = 10.dp, end = 32.dp)
                        .constrainAs(instructionsTitle) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                )
                Text(
                    text = viewModel.mealDetails.value?.instructions ?: "",
                    fontFamily = fontSfPro,
                    fontSize = 15.sp,
                    color = GrayDark,
                    maxLines = if (instructionsExpanded) Int.MAX_VALUE else SHRINK_SIZE,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(horizontal = 32.dp)
                        .constrainAs(instructions) {
                            top.linkTo(instructionsTitle.bottom)
                            start.linkTo(parent.start)
                        }
                )
                ExpandShrinkButton(
                    expanded = instructionsExpanded,
                    onClick = { instructionsExpanded = !instructionsExpanded },
                    modifier = Modifier
                        .padding(start = 32.dp, top = 8.dp, bottom = 16.dp)
                        .constrainAs(expandShrinkButton) {
                            start.linkTo(instructions.start)
                            top.linkTo(instructions.bottom)
                        }
                )
            }
        }
    }
}

@Composable
fun BackButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_left),
            contentDescription = null
        )
    }
}

@Composable
fun ToggleFavoriteButton(
    modifier: Modifier = Modifier,
    mealAddedToFavorite: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(
                id = if (mealAddedToFavorite) R.drawable.ic_heart_red
                else R.drawable.ic_heart
            ),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerWithIndicator(
    modifier: Modifier = Modifier,
    foodUrl: String?,
    videoLink: String?
) {
    val pageState = rememberPagerState()
    val context = LocalContext.current
    val customTabsIntent = CustomTabsIntent.Builder().build()
    val videoThumbId = videoLink?.toUri()?.getQueryParameter("v").toString()
    val hasVideo = !videoLink.isNullOrEmpty()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        HorizontalPager(
            count = if (hasVideo) 2 else 1,
            state = pageState
        ) { pageIndex ->
            if (pageIndex == 0)
                GlideImage(
                    imageModel = foodUrl,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .size(240.dp)
                        .clip(shape = CircleShape)
                )
            else
                Box(contentAlignment = Alignment.Center) {
                    GlideImage(
                        imageModel = stringResource(
                            id = R.string.details_thumb_from_id,
                            videoThumbId
                        ),
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(240.dp)
                            .wrapContentWidth()
                            .noRippleClickable {
                                videoLink
                                    ?.toUri()
                                    ?.let { customTabsIntent.launchUrl(context, it) }
                            }
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_circle_play),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(100.dp)
                    )
                }
        }
        if (hasVideo)
            HorizontalPagerIndicator(
                pagerState = pageState,
                activeColor = RedLight,
                inactiveColor = GrayBright,
                spacing = 12.dp,
            )
    }
}

@Composable
fun MealTag(tag: String) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = RedLight,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = tag,
            color = RedLight,
            textAlign = TextAlign.Center,
            fontFamily = fontSfPro,
            fontSize = 15.sp
        )
    }
}

@Composable
fun ExpandShrinkButton(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Row(modifier = modifier) {
        Box(
            modifier = Modifier
                .border(
                    width = (0.5).dp,
                    color = GrayBright,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .noRippleClickable { onClick() }
        ) {
            Text(
                text = stringResource(id = if (expanded) R.string.details_see_less else R.string.details_see_more),
                color = GrayBright,
                textAlign = TextAlign.Center,
                fontFamily = fontSfPro,
                fontSize = 10.sp
            )
        }
    }
}

@Composable
fun Ingredients(
    modifier: Modifier = Modifier,
    measurement: String?,
    ingredient: String?,
    checked: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(
                id = if (checked) R.drawable.ic_box_checked
                else R.drawable.ic_box_uncheked
            ),
            contentDescription = null,
            modifier = Modifier.noRippleClickable { onChecked(!checked) }
        )
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = buildAnnotatedString {
                append("$measurement ")
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Bold
                    )
                ) {
                    append(ingredient ?: "")
                }
            },
            fontFamily = fontSfPro,
            fontSize = 15.sp,
            color = GrayDark
        )
    }
}

@Preview
@Composable
fun IngredientsItemPreview() {
    var checked by remember { mutableStateOf(false) }
    Ingredients(
        measurement = "1kg",
        ingredient = "Rice",
        checked = checked,
        onChecked = { checked = !checked })
}