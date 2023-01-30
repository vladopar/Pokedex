package com.parizek.myapplication.pokemon_detail_feature.presentation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.parizek.myapplication.R
import com.parizek.myapplication.pokemon_detail_feature.domain.model.Pokemon
import com.parizek.myapplication.ui.theme.almostWhite
import com.parizek.myapplication.ui.theme.grassDark
import com.parizek.myapplication.ui.theme.grassLight
import com.parizek.myapplication.ui.theme.statActive
import com.parizek.myapplication.ui.theme.statBase
import kotlinx.coroutines.delay

@Composable
fun PokemonDetailScreen(
    state: PokemonDetailState,
    viewModel: PokemonDetailViewModel
) {

    var currentId by remember { mutableStateOf(state.pokemon?.id) }

    val currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember {
        Animatable(currentRotation)
    }

    var isContentVisible by remember { mutableStateOf(false) }

    val imageLocation by animateOffsetAsState(
        targetValue = if (isContentVisible) Offset(0F, 0F) else Offset(0f, 1000f),
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )
    val cardLocation by animateOffsetAsState(
        targetValue = if (isContentVisible) Offset(0F, 0F) else Offset(0f, 1000f),
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )
    val mainInfoLocation by animateOffsetAsState(
        targetValue = if (isContentVisible) Offset(0F, 0F) else Offset(0f, -500f),
        animationSpec = tween(1500, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(key1 = true) {
        rotation.animateTo(
            targetValue = currentRotation + 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }
    LaunchedEffect(key1 = currentId) {
        isContentVisible = false
        delay(800)
        viewModel.getPokemonDetail(currentId.toString())
        isContentVisible = true
    }

    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(color = state.pokemon?.colors?.last() ?: grassDark)
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
                colorFilter = ColorFilter.tint(state.pokemon?.colors?.first() ?: grassLight),
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.pokemon?.sprite)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .offset(imageLocation.x.dp, imageLocation.y.dp)
            )
            Card(
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxSize()
                    .offset(cardLocation.x.dp, cardLocation.y.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
                    ),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text(
                        text = "Base Stats",
                        color = Color.Black,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.pokemon?.stats ?: emptyList()) { stats ->

                            var statBarLoading by remember { mutableStateOf(false) }
                            val statValue by animateDpAsState(
                                targetValue = (if (statBarLoading) stats.first.dp else 0.dp),
                                animationSpec = tween(800, 1000, FastOutSlowInEasing)
                            )

                            LaunchedEffect(key1 = true) {
                                statBarLoading = true
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = stats.second,
                                    color = Color.Black,
                                    modifier = Modifier.weight(0.2f)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = stats.first.toString(),
                                    color = Color.Black,
                                    modifier = Modifier.weight(0.2f)
                                )
                                Box(
                                    contentAlignment = Alignment.CenterStart,
                                    modifier = Modifier
                                        .weight(0.8f)
                                        .padding(8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(10.dp)
                                            .background(
                                                color = statBase,
                                                shape = RoundedCornerShape(100)
                                            )
                                    ) {}
                                    Row(
                                        modifier = Modifier
                                            .width(statValue)
                                            .height(10.dp)
                                            .background(
                                                color = statActive,
                                                shape = RoundedCornerShape(100)
                                            )
                                    ) {}
                                }
                            }
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp)
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(0.5f),
                            onClick = {
                                if (state.pokemon?.id!! != 1) {
                                    currentId = state.pokemon.id - 1
                                }
                            }
                        ) {
                            Text(text = "Previous")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(
                            modifier = Modifier
                                .weight(0.5f),
                            onClick = {
                                if (state.pokemon?.id!! != 151) {
                                    currentId = state.pokemon.id + 1
                                }
                            }
                        ) {
                            Text(text = "Next")
                        }
                    }
                }
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
