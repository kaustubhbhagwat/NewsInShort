package com.example.newsinshort.ui.screens.home_screen

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsinshort.ui.Graph
import com.example.newsinshort.ui.screens.SearchNewsScreen
import com.example.newsinshort.ui.screens.article_screen.ArticleScreen
import com.example.newsinshort.ui.screens.breaking_news.BreakingNewsScreen
import com.example.newsinshort.ui.screens.news_screen.NewsScreenViewModel
import com.example.newsinshort.ui.screens.saved_screen.SavedNewsScreen
import com.example.newsinshort.utils.BottomBarScreen

@Composable
fun HomeNavGraph(navController: NavHostController) {

    val snackBarState = remember { (SnackbarHostState()) }
    val argKey = "webUrl"


    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.BreakingNews.route
    ) {

        composable(route = BottomBarScreen.BreakingNews.route) {
            val viewModel: NewsScreenViewModel = hiltViewModel()
           BreakingNewsScreen(navController = navController)
        }


        composable(route = BottomBarScreen.SavedNews.route) {
            SavedNewsScreen(
                navController = navController,
            )
        }

        composable(route = BottomBarScreen.SearchNews.route){
            SearchNewsScreen(navController = navController)
        }


        composable(
            route = "article_screen?$argKey={$argKey}",
            arguments = listOf(navArgument(name = argKey) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            ArticleScreen(
                url = backStackEntry.arguments?.getString(argKey),
                onBackPressed = { navController.navigateUp() }
            )
        }

    }
//    }

}