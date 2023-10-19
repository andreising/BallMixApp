package com.andreisingeleytsev.ballmixapp.ui.screen.menu_screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.andreisingeleytsev.ballmixapp.R
import com.andreisingeleytsev.ballmixapp.ui.utils.Routes

@Composable
fun MenuScreen(navHostController: NavHostController) {
    val isFirstScreen = remember {
        mutableStateOf(true)
    }
    BackHandler {
        if (isFirstScreen.value) navHostController.popBackStack()
        else isFirstScreen.value = true
    }
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Image(
            painter = painterResource(id = R.drawable.menu_bg),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        if (isFirstScreen.value) Image(
            painter = painterResource(id = R.drawable.btn_start),
            contentDescription = null,
            modifier = Modifier
                .padding(bottom = 50.dp)
                .fillMaxWidth()
                .clickable { isFirstScreen.value = false },
            contentScale = ContentScale.FillWidth
        ) else Column(modifier = Modifier.padding(bottom = 10.dp)) {
            Image(
                painter = painterResource(id = R.drawable.btn_easy),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navHostController.navigate(Routes.GAME_SCREEN + "/0") },
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(5.dp))
            Image(
                painter = painterResource(id = R.drawable.btn_normal),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navHostController.navigate(Routes.GAME_SCREEN + "/1") },
                contentScale = ContentScale.FillWidth
            )
        }
    }
}