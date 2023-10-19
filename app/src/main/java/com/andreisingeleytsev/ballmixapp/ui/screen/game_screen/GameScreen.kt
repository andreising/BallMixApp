package com.andreisingeleytsev.ballmixapp.ui.screen.game_screen

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.andreisingeleytsev.ballmixapp.R

@Composable
fun GameScreen(
    navHostController: NavHostController,
    viewModel: GameScreenViewModel = hiltViewModel()
) {
    val gameState = viewModel.gameState
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.game_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillWidth
        )
        if (gameState.value == GameState.OnChoose) Text(
            text = "Choose a barrel", style = TextStyle(
                color = Color.White, fontSize = 24.sp, textAlign = TextAlign.Center
            ), modifier = Modifier
                .padding(horizontal = 10.dp)
                .fillMaxSize()
        )
        if (gameState.value == GameState.Finish) Image(
            painter = painterResource(
                id = if (viewModel.isWin) R.drawable.win_title
                else R.drawable.lose_title
            ),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            contentScale = ContentScale.FillWidth
        )
        if (gameState.value == GameState.Start) Image(
            painter = painterResource(id = R.drawable.btn_mix),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 20.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .clickable {
                    viewModel.onEvent(GameScreenEvent.OnMixPressed)
                },
            contentScale = ContentScale.FillWidth
        )
        if (gameState.value == GameState.Finish) Column(
            modifier = Modifier
                .padding(vertical = 10.dp, horizontal = 20.dp)
                .align(Alignment.BottomCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.btn_play_more),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        viewModel.onEvent(GameScreenEvent.OnRestart)
                    },
                contentScale = ContentScale.FillWidth
            )
            Image(
                painter = painterResource(id = R.drawable.btn_menu),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navHostController.popBackStack()
                    },
                contentScale = ContentScale.FillWidth
            )
        }
        viewModel.difficulty?.let { difficulty ->
            val index = difficulty.index
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            ) {
                val barrelWidth = maxWidth / index - 4.dp
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                ) {
                    for (int in 0 until index) {
                        val currentPosition by animateDpAsState(
                            targetValue = viewModel.currentList.value.indexOf(int) * barrelWidth,
                            animationSpec = tween(700), label = ""
                        )
                        BarrelUIItem(
                            isBall = when (int) {
                                1 -> true
                                2 -> false
                                else -> null
                            },
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .offset(x = currentPosition)
                                .width(barrelWidth)

                                .clickable(viewModel.gameState.value == GameState.OnChoose) {
                                    viewModel.onEvent(
                                        GameScreenEvent.OnBarrelChose(int = int)
                                    )
                                },
                            barrelOffset = if (viewModel.barrelsIsUp.value && int in (1..2)) -60.dp else 0.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BarrelUIItem(isBall: Boolean?, modifier: Modifier, barrelOffset: Dp) {
    val barrelAnimateOffset by animateDpAsState(
        targetValue = barrelOffset,
        animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing), label = ""
    )
    Box(modifier = modifier, contentAlignment = Alignment.BottomCenter) {
        isBall?.let {
            Image(
                painter = painterResource(id = if (it) R.drawable.img_ball else R.drawable.img_bomb),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )
        }
        Image(
            painter = painterResource(id = R.drawable.img_barrel),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = barrelAnimateOffset),
            contentScale = ContentScale.FillWidth
        )
    }
}