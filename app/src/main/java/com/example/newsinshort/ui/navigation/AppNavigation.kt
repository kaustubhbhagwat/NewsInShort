package com.example.newsinshort.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsinshort.ui.screens.HomeScreen

@Composable
fun AppNavigationGraph(){

    val navContorller = rememberNavController()
    NavHost(navController = navContorller, startDestination =Routes.HOME_SCREEN ){
        composable(Routes.HOME_SCREEN) {
            HomeScreen()
        }
    }
}