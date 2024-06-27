package com.example.newsinshort.ui.screens.saved_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Observer
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
    val lifecycleOwner = LocalLifecycleOwner.current
    Surface(color = Color.Black, modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                text = "Saved Articles",
                modifier = Modifier
                    .fillMaxWidth(),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.size(8.dp))
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                savedNewsViewModel.allSavedNews.observe(lifecycleOwner, Observer {
                    items(it) { article ->
                        SavedArticleCard(article = article)
                    }
                })
            }
        }
    }
}