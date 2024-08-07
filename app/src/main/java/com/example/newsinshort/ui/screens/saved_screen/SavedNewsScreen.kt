package com.example.newsinshort.ui.screens.saved_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsinshort.data.database.SavedNewsViewModel
import com.example.newsinshort.ui.components.SavedArticleCard

@Composable
fun SavedNewsScreen(
    navController: NavController,
    savedNewsViewModel: SavedNewsViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Saved News",
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Articles: " + savedNewsViewModel.allSavedNews.value?.size.toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                savedNewsViewModel.allSavedNews.observe(lifecycleOwner) {
                    items(it) { article ->
                        SavedArticleCard(navController = navController, article = article)
                    }
                }
            }
        }
    }
}