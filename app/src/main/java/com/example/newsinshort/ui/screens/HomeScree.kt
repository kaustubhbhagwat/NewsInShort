package com.example.newsinshort.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsinshort.ui.components.Loader
import com.example.newsinshort.ui.components.NewsRowComponent
import com.example.newsinshort.ui.viewmodel.NewsViewModel
import com.example.utilities.ResourceState

const val TAG = "HOMESCREEN"

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    newsViewModel: NewsViewModel = hiltViewModel()
) {
    val newsResponse by newsViewModel.news.collectAsState()
    var pageCount = 0

        when (newsResponse) {
            is ResourceState.Loading -> {
                Loader()
                Log.d(TAG, "Loading")
            }
            is ResourceState.Error -> {
                val error = (newsResponse as ResourceState.Error)
                Log.d(TAG, "$error")
            }
            is ResourceState.Success -> {
                pageCount = (newsResponse as ResourceState.Success).data.articles.size
            }
        }

    val pagerState = rememberPagerState(pageCount = { pageCount }, initialPage = 0, initialPageOffsetFraction = 0f)
    VerticalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxSize(),
        pageSize = PageSize.Fill,
        pageSpacing = 8.dp
    ) { page ->
                val response = (newsResponse as ResourceState.Success).data
                if (response.articles.isNotEmpty() && page < response.articles.size) {
                    NewsRowComponent(page, response.articles[page])
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}