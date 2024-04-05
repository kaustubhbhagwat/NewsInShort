package com.example.newsinshort.ui.screens.news_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.example.newsinshort.data.entity.Article
import com.example.newsinshort.ui.components.BottomSheetContent
import com.example.newsinshort.ui.components.NewsArticleCard
import com.example.newsinshort.ui.components.RetryContent
import com.example.newsinshort.ui.components.SearchAppBar
import kotlinx.coroutines.launch


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

    if (shouldShowBottomSheet) {
        ModalBottomSheet(onDismissRequest = { shouldShowBottomSheet = false },
            sheetState = stateSheet,
            content = {
                state.selectedArticle?.let {
                    BottomSheetContent(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            onReadFullStoryButtonClick(it.url)
                            coroutineScope.launch { stateSheet.hide() }.invokeOnCompletion {
                                if (!stateSheet.isVisible) shouldShowBottomSheet = false
                            }
                        }
                    )
                }
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Crossfade(targetState = state.isSearchBarVisible) { isVisible ->
            if (isVisible) {
                Column {
                    SearchAppBar(
                        modifier = Modifier.focusRequester(focusRequester),
                        value = state.searchQuery,
                        onValueChange = { newValue ->
                            onEvent(NewsScreenEvent.onSearchQueryChanged(newValue))
                        },
                        onCloseIconClicked = { onEvent(NewsScreenEvent.onCloseIconClick) },
                        onSearchClicked = {
                            keyboardController?.hide()
                            focusManager.clearFocus()
                        }
                    )


                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsArticleList(
    state: NewsState,
    onCardClicker: (Article) -> Unit,
    onRetry: () -> Unit
) {

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        
        items(state.articles) { article ->
            NewsArticleCard(
                article = article,
                onCardClicked = onCardClicker
            )
        }
    }
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        if(state.isLoading){
            CircularProgressIndicator()
        }
        if(state.error != null){
            RetryContent(error = state.error, onRetry = onRetry)
        }
    }
}
