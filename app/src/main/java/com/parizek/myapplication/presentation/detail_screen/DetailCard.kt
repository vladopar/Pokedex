package com.parizek.myapplication.presentation.detail_screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailCard(
    cardLocation: Offset,
    state: PokemonDetailState,
    isLoadedFromMainScreen: Boolean,
    modifier: Modifier
) {
    val tabs = remember { mutableStateListOf("Base Stats", "tab2") }
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
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 30.dp)
        ) {
//            TabRow(
//                selectedTabIndex = tabState,
//                modifier = Modifier
//                    .padding(top = 32.dp, start = 16.dp, end = 16.dp)
//                    .background(Color.White),
//                containerColor = Color.White,
//                indicator = { tabPositions ->
//                    TabRowDefaults.Indicator(
//                        modifier = Modifier.tabIndicatorOffset(
//                            currentTabPosition = tabPositions[tabState],
//                        )
//                    )
//                }
//            ) {
//                tabs.forEachIndexed { index, tab ->
//                    Tab(
//                        selected = tabState == index,
//                        onClick = { tabState = index },
//                        modifier = Modifier
//                            .height(36.dp),
//                        text = { Text(text = tab, color = Color.Black)},
//                    )
//                }
//            }
            Text(
                text = "Base Stats",
                color = Color.Black,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(32.dp)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                state.pokemon?.stats?.forEach{ stats ->

                    var statChange by remember { mutableStateOf(false) }
                    val statValue by animateDpAsState(
                        targetValue = (if (statChange) stats.first.dp else 0.dp),
                        animationSpec = tween(800, if(isLoadedFromMainScreen) 600 else 0, FastOutSlowInEasing)
                    )

                    LaunchedEffect(key1 = true) {
                        statChange = true
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
                        AnimatedContent(
                            targetState = stats.first,
                            transitionSpec = { slideInHorizontally { -it } with slideOutHorizontally { it } },
                            modifier = Modifier.weight(0.2f)
                        ) {
                            Text(
                                text = stats.first.toString(),
                                color = Color.Black,
                                modifier = Modifier.weight(0.2f)
                            )
                        }
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
        }
    }
}