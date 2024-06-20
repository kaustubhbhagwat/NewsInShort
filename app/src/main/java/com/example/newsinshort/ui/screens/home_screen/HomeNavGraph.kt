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
import com.example.newsinshort.data.database.model.SavedArticle
import com.example.newsinshort.ui.Graph
import com.example.newsinshort.ui.components.WebViewScreen
import com.example.newsinshort.ui.screens.SearchNewsScreen
import com.example.newsinshort.ui.screens.article_screen.ArticleScreen
import com.example.newsinshort.ui.screens.breaking_news.BreakingNewsScreen
import com.example.newsinshort.ui.screens.news_screen.NewsScreenViewModel
import com.example.newsinshort.ui.screens.saved_screen.SavedNewsScreen
import com.example.newsinshort.utils.BottomBarScreen
import com.squareup.moshi.Moshi

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
                navController = navController
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

        /*
   This composable is for each article. An article should be passed into this function
   Via it's route parameter/navArguments. It is then transferred over via moshi and passed
   into the WebViewScreen to access the current article
    */
        composable(
            route = "saved_news/{article}",
            arguments = listOf(navArgument("article") { type = NavType.StringType })
        ) { backStackEntry ->
            val articleJson = backStackEntry.arguments?.getString("article")
            val moshi = Moshi.Builder().build()
            val jsonAdapter = moshi.adapter(SavedArticle::class.java).lenient()
            val currentArticle = jsonAdapter.fromJson(articleJson)

            // WebViewScreen(url = "https://www.google.com")
            if (currentArticle != null) {
                WebViewScreen(currentArticle)
            }
        }

    }
//    }

}