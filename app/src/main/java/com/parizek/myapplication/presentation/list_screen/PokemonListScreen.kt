package com.parizek.myapplication.presentation.list_screen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.parizek.myapplication.R
import com.parizek.myapplication.domain.model.PokemonListData
import com.parizek.myapplication.ui.theme.almostWhite
import kotlinx.coroutines.launch

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onClick: (Int) -> Unit
) {

    val pokemons = viewModel.getPokemonListPaginated().collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()

    val listState = viewModel.listState

    val scope = rememberCoroutineScope()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = Color.Black,
            darkIcons = false
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            state = listState,

            ) {
            items(
                pokemons.itemCount,
            ) { index ->
                pokemons[index].let { pokemon ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = pokemon?.colorsForList?.last() ?: Color.White
                        ),
                        modifier = Modifier
                            .height(150.dp)
                            .clickable {
                                onClick(pokemon?.id ?: 0)
                            }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .align(Alignment.TopStart)
                            ) {
                                Text(
                                    text = pokemon?.nameForList?.replaceFirstChar { it.uppercase() }
                                        ?: "",
                                    fontWeight = FontWeight.Bold,
                                    color = almostWhite
                                )
                                Text(
                                    text = "#${pokemon?.idForList ?: "000"}",
                                    fontWeight = FontWeight.Bold,
                                    color = almostWhite
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.pokeball_png),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(
                                    pokemon?.colorsForList?.first() ?: Color.LightGray
                                ),
                                modifier = Modifier
                                    .size(200.dp)
                                    .align(Alignment.BottomEnd)
                                    .offset(x = 50.dp, y = 20.dp)
                            )
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(pokemon?.spriteForList ?: "")
                                    .scale(Scale.FIT)
                                    .build(),
                                contentDescription = null,
                                loading = {
                                    CircularProgressIndicator()
                                },
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.BottomEnd)
                                    .offset(x = 10.dp, y = 10.dp)
                            )
                        }
                    }
                }
            }
            when (val state = pokemons.loadState.refresh) {
                is LoadState.Error -> {

                }

                is LoadState.Loading -> {
                    item {
                        val currentRotation by remember { mutableStateOf(0f) }

                        val rotation = remember {
                            Animatable(currentRotation)
                        }
                        LaunchedEffect(key1 = true) {
                            rotation.animateTo(
                                targetValue = currentRotation + 360f,
                                animationSpec = infiniteRepeatable(
                                    animation = tween(3000, easing = FastOutSlowInEasing),
                                    repeatMode = RepeatMode.Restart
                                )
                            )
                        }
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
//                            CircularProgressIndicator(color = Color.Black)
                            Image(
                                painter = painterResource(id = R.drawable.pokeball_png),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(Color.LightGray),
                                modifier = Modifier
                                    .rotate(rotation.value)
                                    .size(250.dp)
                            )
                        }
                    }
                }

                else -> {}
            }

            when (val state = pokemons.loadState.append) {
                is LoadState.Error -> {

                }

                is LoadState.Loading -> {
                    item {
                        PlaceholderCard()
                    }
                    item {
                        PlaceholderCard()
                    }
                }

                else -> {}
            }
        }
    }
}

@Composable
fun PlaceholderCard() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.DarkGray
        ),
        modifier = Modifier
            .height(150.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.TopStart)
            ) {
                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    color = almostWhite
                )
                Text(
                    text = "",
                    fontWeight = FontWeight.Bold,
                    color = almostWhite
                )
            }
            Image(
                painter = painterResource(id = R.drawable.pokeball_png),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.LightGray),
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 50.dp, y = 20.dp)
            )
            CircularProgressIndicator(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 10.dp)
            )
        }
    }
}