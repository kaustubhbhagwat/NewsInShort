package com.example.newsinshort.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryTabRow(
    pagerState: PagerState,
    categories: List<String>,
    onTabSelected: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        containerColor = Color.Black,
        contentColor = Color.White,
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selectedContentColor = Color.White,
                unselectedContentColor = Color.DarkGray,
                selected = pagerState.currentPage == index,
                onClick = { onTabSelected(index) },
                content = {
                    Text(
                        text = category,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 2.dp)
                    )
                }
            )
        }
    }
}