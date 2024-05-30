package com.example.newsinshort.ui.screens.breaking_news

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.pm.ShortcutInfoCompat.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsinshort.ui.screens.news_screen.NewsScreen
import com.example.newsinshort.ui.screens.news_screen.NewsScreenViewModel

@Composable
fun BreakingNewsScreen(
    navController: NavController
) {
    val viewModel: NewsScreenViewModel = hiltViewModel()
    val argKey = "webUrl"

    Surface(modifier = Modifier.fillMaxSize()) {
        NewsScreen(
            state = viewModel.state,
            onEvent = viewModel::onEvent,
            onReadFullStoryButtonClick = { url ->
                navController.navigate("article_screen?$argKey=$url")
            })
    }

}