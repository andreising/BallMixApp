package com.andreisingeleytsev.ballmixapp.ui.navigation


import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.andreisingeleytsev.ballmixapp.common.Constants
import com.andreisingeleytsev.ballmixapp.ui.screen.game_screen.GameScreen
import com.andreisingeleytsev.ballmixapp.ui.screen.menu_screen.MenuScreen
import com.andreisingeleytsev.ballmixapp.ui.utils.Routes


@Composable
fun BallMixAppMainNavigationGraph() {
    val navHostController = rememberNavController()
    NavHost(
        navController = navHostController, startDestination = Routes.MENU_SCREEN,
        modifier = Modifier.background(Color.Transparent)
    ) {
        composable(Routes.MENU_SCREEN){
            MenuScreen(navHostController = navHostController)
        }
        composable(Routes.GAME_SCREEN+"/{${Constants.DIFFICULTY}}"){
            GameScreen(navHostController = navHostController)
        }
    }
}
