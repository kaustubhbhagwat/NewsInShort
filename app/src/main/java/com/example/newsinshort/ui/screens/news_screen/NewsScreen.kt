package com.example.newsinshort.ui.screens.news_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun NewsScreen(
    state: NewsState,
    onEvent: (NewsScreenEvent) -> Unit,
    onReadFullStoryButtonClicked: (String) -> Unit
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState(
        pageCount = { 50 },
        initialPage = 0,
        initialPageOffsetFraction = 0f
    )
    val coroutineScope = rememberCoroutineScope()

    val categories =
        listOf("General", "Business", "Technology", "Health", "Science", "Entertainment")

    val focusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect{
            page -> onEvent(NewsScreenEvent.onCategoryChanged(category =categories[page]))
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(NewsScreenEvent.onSearchQueryChanged(searchQuery = state.searchQuery))


    }

}