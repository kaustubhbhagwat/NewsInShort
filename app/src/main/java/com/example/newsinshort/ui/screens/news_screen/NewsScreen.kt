package com.example.newsinshort.ui.screens.news_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import com.example.newsinshort.ui.components.BottomSheetContent


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalComposeApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun NewsScreen(
    state: NewsState,
    onEvent: (NewsScreenEvent) -> Unit,
    onReadFullStoryButtonClick: (String) -> Unit
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
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onEvent(NewsScreenEvent.onCategoryChanged(category = categories[page]))
        }
    }

    LaunchedEffect(key1 = Unit) {
        onEvent(NewsScreenEvent.onSearchQueryChanged(searchQuery = state.searchQuery))
    }

    val stateSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var shouldShowBottomSheet by remember { mutableStateOf(false) }

    if(shouldShowBottomSheet){
        ModalBottomSheet(onDismissRequest = {shouldShowBottomSheet = false},
            sheetState = stateSheet,
            content = {
                state.selectedArticle?.let {
                    BottomSheetContent(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            onReadFullStoryButtonClick(it.url)
                        }
                    )
                }
            }
        )
    }


}