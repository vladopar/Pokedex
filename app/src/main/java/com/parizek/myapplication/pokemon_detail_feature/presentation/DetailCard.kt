package com.parizek.myapplication.pokemon_detail_feature.presentation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.parizek.myapplication.ui.theme.statActive
import com.parizek.myapplication.ui.theme.statBase

@Composable
fun DetailCard(
    cardLocation: Offset,
    state: PokemonDetailState,
    onClick: (Int) -> Unit,
    modifier: Modifier
    ) {
    val tabs = remember { mutableStateListOf("tab1","tab2") }
    var tabState by remember { mutableStateOf(0) }
    Card(
        shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier
            .fillMaxSize()
            .offset(cardLocation.x.dp, cardLocation.y.dp)
            .background(
                color = Color.Transparent,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp),
            ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
        ) {
//            TabRow(
//                selectedTabIndex = tabState,
//                modifier = Modifier
//                    .padding(16.dp)
//            ) {
//                tabs.forEachIndexed { index, tab ->
//                    Tab(selected = tabState == index, onClick = { tabState = index }) {
//                        Text("SomeText $index $tab")
//                    }
//                }
//            }
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
                    .weight(1f, false)
                    .padding(32.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(0.5f),
                    onClick = {
                        if (state.pokemon?.id!! != 1) {
                            onClick(-1)
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
                            onClick(1)
                        }
                    }
                ) {
                    Text(text = "Next")
                }
            }
        }
    }
}