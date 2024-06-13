package com.example.newsinshort.ui.screens.saved_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsinshort.data.database.SavedNewsViewModel

@Composable
fun SavedNewsScreen(
    navController: NavController,
    savedNewsViewModel: SavedNewsViewModel = hiltViewModel()
) {

    savedNewsViewModel.allSavedNews.observeForever {
        it.size
    }

    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
    }
}