package com.example.newsinshort.ui.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsinshort.ui.screens.HomeScreen
import com.example.newsinshort.ui.theme.DarkGray


@Composable
fun AppNavigationGraph(){

    Surface(
        color = DarkGray
    ){
        val navContorller = rememberNavController()
        NavHost(navController = navContorller, startDestination =Routes.HOME_SCREEN ){
            composable(Routes.HOME_SCREEN) {
                HomeScreen()
            }
        }
    }

}