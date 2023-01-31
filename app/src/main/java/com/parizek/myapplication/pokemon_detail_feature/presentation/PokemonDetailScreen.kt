package com.parizek.myapplication.pokemon_detail_feature.presentation

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.parizek.myapplication.R
import com.parizek.myapplication.pokemon_detail_feature.domain.model.Pokemon
import com.parizek.myapplication.ui.theme.almostWhite
import com.parizek.myapplication.ui.theme.grassDark
import com.parizek.myapplication.ui.theme.grassLight
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
@Composable
fun PokemonDetailScreen(
    viewModel: PokemonDetailViewModel,
) {

    val state = viewModel.state

    val systemUiController = rememberSystemUiController()

    val currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember {
        Animatable(currentRotation)
    }

    var isContentVisibleOnEnter by remember { mutableStateOf(false) }

    var isLoadedFromMainScreen by remember { mutableStateOf(true) }

    var swipeVisibility by remember { mutableStateOf(true) }

    val imageLocation by animateOffsetAsState(
        targetValue = if (isContentVisibleOnEnter) Offset(0F, 0F) else Offset(0f, 1000f),
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )
    val cardLocation by animateOffsetAsState(
        targetValue = if (isContentVisibleOnEnter) Offset(0F, 0F) else Offset(0f, 1000f),
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )
    val mainInfoLocation by animateOffsetAsState(
        targetValue = if (isContentVisibleOnEnter) Offset(0F, 0F) else Offset(0f, -500f),
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )
    val darkBackgroundColor by animateColorAsState(
        targetValue = state.pokemon?.colors?.last() ?: Color.DarkGray,
        animationSpec = tween(1000, 0, LinearEasing)
    )
    val lightBackgroundColor by animateColorAsState(
        targetValue = state.pokemon?.colors?.first() ?: Color.LightGray,
        animationSpec = tween(1000, 0, LinearEasing)
    )

    val width = LocalConfiguration.current.screenWidthDp
    val swipeableState = rememberSwipeableState(initialValue = "A")
    val sizePx = (width).toFloat()
    val anchors = mapOf(0f to "A", -3 * sizePx to "B", 3 * sizePx to "C")
    val scope = rememberCoroutineScope()

    if (swipeableState.isAnimationRunning) {
        DisposableEffect(Unit) {
            onDispose {
                isLoadedFromMainScreen = false
                when (swipeableState.currentValue) {
                    "B" -> {
                        if (state.pokemon?.id!! != 151) {
                            viewModel.updateCurrentId(1)
                        } else scope.launch { swipeableState.animateTo("A") }
                    }

                    "C" -> {
                        if (state.pokemon?.id!! != 1) {
                            viewModel.updateCurrentId(-1)
                        } else scope.launch { swipeableState.animateTo("A") }
                    }

                    else -> {
                        return@onDispose
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        isContentVisibleOnEnter = true
        rotation.animateTo(
            targetValue = currentRotation + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    SideEffect {
        systemUiController.setStatusBarColor(
            color = darkBackgroundColor,
            darkIcons = false
        )
    }

    LaunchedEffect(key1 = state.currentId) {
        viewModel.getPokemonDetail(state.currentId.toString())
        swipeVisibility = false
        when (swipeableState.currentValue) {
            "B" -> {
                swipeableState.animateTo("C")
            }

            "C" -> {
                swipeableState.animateTo("B")
            }
        }
        swipeVisibility = true
        swipeableState.animateTo(
            targetValue = "A",
        )
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(color = darkBackgroundColor)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal,
                thresholds = { _, _ -> FractionalThreshold(0.3f) }
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(120.dp))
            Image(
                painter = painterResource(id = R.drawable.pokeball_png),
                contentDescription = null,
                colorFilter = ColorFilter.tint(lightBackgroundColor),
                modifier = Modifier
                    .rotate(rotation.value)
                    .size(250.dp)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            AnimatedContent(
                targetState = state.pokemon,
                transitionSpec = {
                    slideInVertically { -it } + fadeIn() with
                            slideOutVertically { it } + fadeOut()
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = mainInfoLocation.x.dp, y = mainInfoLocation.y.dp)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
                    ) {
                        Text(
                            text = "${state.pokemon?.name}",
                            color = almostWhite,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "#${state.pokemon?.idString}",
                            color = almostWhite,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.pokemon?.types ?: emptyList()) { type ->
                            Text(
                                text = type.second,
                                style = MaterialTheme.typography.bodyMedium,
                                color = almostWhite,
                                modifier = Modifier
                                    .background(
                                        color = state.pokemon?.colors?.first() ?: grassLight,
                                        shape = RoundedCornerShape(100)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                        }
                    }

                }

            }
            Spacer(modifier = Modifier.weight(0.3f))
            DetailCard(
                cardLocation = cardLocation,
                state = state,
                isLoadedFromMainScreen = isLoadedFromMainScreen,
                modifier = Modifier
                    .weight(0.7f)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(140.dp))
            AnimatedVisibility(
                visible = swipeVisibility,
                enter = EnterTransition.None,
                exit = ExitTransition.None,
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.pokemon?.sprite)
                        .scale(Scale.FIT)
                        .build(),
                    contentDescription = null,
                    alignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .size(270.dp)
                        .offset(imageLocation.x.dp, imageLocation.y.dp)
                        .offset {
                            IntOffset(swipeableState.offset.value.roundToInt(), 0)
                        }
                )
            }
        }
    }
}

val previewPokemon = Pokemon(
    id = 1,
    idString = "001",
    name = "Bulbasaur",
    height = 7,
    weight = 69,
    sprite = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/1.svg",
    stats = listOf(
        49 to "attack",
        45 to "hp",
        49 to "defense",
        65 to "special-defense",
        65 to "special-attack",
        45 to "speed"
    ),
    types = listOf(
        1 to "grass",
        2 to "poison"
    ),
    colors = listOf(grassLight, grassDark)
)


/*
@Preview
@Composable
fun PokemonDetailScreenPreview() {
    PokedexTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            PokemonDetailScreen(
                state = PokemonDetailState(previewPokemon),
            )
        }
    }

}*/
