package com.example.newsinshort.ui.navigation

//import BottomNavigationExample
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.newsinshort.ui.screens.article_screen.ArticleScreen
import com.example.newsinshort.ui.screens.news_screen.NewsScreen
import com.example.newsinshort.ui.screens.news_screen.NewsScreenViewModel

@Composable
fun NavGraphSetup(
    navController: NavHostController
) {
    val argKey = "webUrl"

    NavHost(
        navController = navController,
        startDestination = "news_screen"
    ) {
        composable(route = "news_screen"){
            val viewModel: NewsScreenViewModel = hiltViewModel()
            NewsScreen(state = viewModel.state,
                onEvent = viewModel::onEvent,
                onReadFullStoryButtonClick = { url ->
                    navController.navigate("article_screen?$argKey=$url")
                }
            )
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

//        composable(
//            route = "main_screen",
//            arguments = listOf(navArgument(name = argKey) {
//                type = NavType.StringType
//            })
//        ) { backStackEntry ->
//            BottomNavigationExample(
//            )
//        }
    }
}