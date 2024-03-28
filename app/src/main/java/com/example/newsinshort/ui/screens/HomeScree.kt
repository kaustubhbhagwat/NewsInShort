package com.example.newsinshort.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsinshort.ui.viewmodel.NewsViewModel


@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
){
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "HomeScreen")
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}