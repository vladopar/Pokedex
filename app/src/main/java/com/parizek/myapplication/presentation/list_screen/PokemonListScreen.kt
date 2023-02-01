package com.parizek.myapplication.presentation.list_screen

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.rememberBackdropScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.parizek.myapplication.R

@Composable
fun PokemonListScreen(
    viewModel: PokemonListViewModel,
    onClick: (String) -> Unit
) {

    val pokemonList = viewModel.pokemonList
    val endReached = viewModel.endReached
    val isLoading = viewModel.isLoading

    val systemUiController = rememberSystemUiController()


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
        if (pokemonList.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
            ) {
                itemsIndexed(pokemonList) { index, pokemon ->
                    if (index >= pokemonList.size - 1 && !endReached && !isLoading) {
                        viewModel.getPokemonList()
                    }
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = pokemon.colorsForList.last()
                        ),
                        modifier = Modifier
                            .height(150.dp)
                            .clickable {
                                onClick(pokemon.nameForList)
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
                                    text = pokemon.nameForList.replaceFirstChar { it.uppercase() },
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "#${pokemon.idForList}",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Image(
                                painter = painterResource(id = R.drawable.pokeball_png),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(pokemon.colorsForList.first()),
                                modifier = Modifier
                                    .size(200.dp)
                                    .align(Alignment.BottomEnd)
                                    .offset(x = 50.dp, y = 20.dp)
                            )
                            SubcomposeAsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(pokemon.spriteForList)
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
        }
    }
}