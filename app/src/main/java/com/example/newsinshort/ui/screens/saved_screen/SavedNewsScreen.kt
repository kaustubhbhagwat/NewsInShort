package com.example.newsinshort.ui.screens.saved_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsinshort.data.database.SavedNewsViewModel

@Composable
fun SavedNewsScreen(
    navController: NavController
) {

    val savedNewsViewModel : SavedNewsViewModel = hiltViewModel()

    val savedNews = savedNewsViewModel.savedNews.collectAsState()
    val savedNews2 = savedNewsViewModel.savedNews
    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
    }
}