package com.example.newsinshort.ui.screens.saved_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsinshort.data.database.SavedNewsViewModel
import com.example.newsinshort.data.database.model.SavedArticle
import com.example.newsinshort.ui.components.SavedArticleCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SavedNewsScreen(
    navController: NavController,
    savedNewsViewModel: SavedNewsViewModel = hiltViewModel()
) {
    var list: List<SavedArticle>
    savedNewsViewModel.allSavedNews.observeForever {
        it.size
        list = it
    }
    Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            savedNewsViewModel.allSavedNews.observeForever {
                it.size
                list = it
                items(list) { article ->
                    SavedArticleCard(article = article)
                }
            }
        }

    }
}