package com.example.newsinshort.ui.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.newsinshort.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    onSearchIconClicked: () -> Unit
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { Text(text = "Flash Feed", fontWeight = FontWeight.Bold)},
        actions = {
            IconButton(onClick = onSearchIconClicked) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Black,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        )
    )
}