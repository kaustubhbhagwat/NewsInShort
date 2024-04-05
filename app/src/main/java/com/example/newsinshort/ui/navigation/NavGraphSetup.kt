package com.example.newsinshort.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsinshort.ui.screens.news_screen.NewsScreen

@Composable
fun NavGraph(
    navController: NavHostController
) {
    val arg_key = "webUrl"

    NavHost(
        navController = navController,
        startDestination = "news_Screen"
    ) {
        composable(route = "news_screem"){

            NewsScreen(state = vieModel.state,
                onEvent = viewModel::onEvent,
            ) {

            }
        }

    }

}