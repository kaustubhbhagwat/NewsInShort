package com.example.newsinshort.ui.navigation

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsinshort.ui.screens.article_screen.ArticleScreen
import com.example.newsinshort.ui.screens.home_screen.HomeScreen
import com.example.newsinshort.ui.theme.DarkGray


@Composable
fun AppNavigationGraph(){
    val argKey = "web_url"
    Surface(
        color = DarkGray
    ){
        val navContorller = rememberNavController()
        NavHost(navController = navContorller, startDestination =Routes.HOME_SCREEN ){

            composable(Routes.HOME_SCREEN) {
                HomeScreen(
                    onReadFullStoryButtonClick = {
                        url ->
                        navContorller.navigate("article_screen?$argKey={$argKey}")
                    }
                )
            }

            composable(route = "article_screen?$argKey={$argKey}",
                arguments = listOf(navArgument(name = argKey){
                    type= NavType.StringType
                })
            ){
                navBackStackEntry ->
                ArticleScreen(
                    url = navBackStackEntry.arguments?.getString(argKey)
                ) { navContorller.navigateUp() }
            }
        }
    }

}