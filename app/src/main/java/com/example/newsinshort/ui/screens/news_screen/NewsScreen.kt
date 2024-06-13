package com.example.newsinshort.ui.screens.news_screen

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager

import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsinshort.data.database.SavedNewsViewModel
import com.example.newsinshort.data.database.entities.Article
import com.example.newsinshort.data.database.model.SavedArticle
import com.example.newsinshort.data.database.model.Source
import com.example.newsinshort.ui.components.BottomSheetContent
import com.example.newsinshort.ui.components.CategoryTabRow
import com.example.newsinshort.ui.components.NewsArticleCard
import com.example.newsinshort.ui.components.NewsScreenTopBar
import com.example.newsinshort.ui.components.RetryContent
import com.example.newsinshort.ui.components.SearchAppBar
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.net.URLEncoder


@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun NewsScreen(
    navController: NavController,
    state: NewsState,
    onEvent: (NewsScreenEvent) -> Unit,
    onReadFullStoryButtonClick: (String) -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val coroutineScope = rememberCoroutineScope()

    val categories =
        listOf("General", "Business", "Technology", "Health", "Science", "Entertainment")

    val focusRequester = remember {
        FocusRequester()
    }
    val horizontalPagerState = rememberPagerState(
        pageCount = { categories.size },
        initialPage = 0,
        initialPageOffsetFraction = 0f
    )

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val savedNewsViewModel: SavedNewsViewModel = hiltViewModel()

    LaunchedEffect(key1 = horizontalPagerState) {
        snapshotFlow { horizontalPagerState.currentPage }.collect { page ->
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
            containerColor = Color.DarkGray,
            sheetState = stateSheet,
            content = {
                state.selectedArticle?.let {

                    val savedArticle = SavedArticle(
                        title = it.title,
                        author = it.author,
                        description = it.description,
                        content = it.content,
                        publishedAt = it.publishedAt,
                        source = Source(name = it.source?.name.toString(), id = it.source?.id.toString()),
                        url = it.url,
                        urlToImage = it.urlToImage,
                    )

                    BottomSheetContent(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            savedNewsViewModel.saveNews(savedArticle)
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
                Column(modifier = Modifier.background(Color.Black)) {
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
                    NewsArticleList(
                        navController = navController,
                        state = state, onCardClicked = { article ->
                            shouldShowBottomSheet = true
                            onEvent(NewsScreenEvent.onNewsCardClicked(article = article))
                        }, onRetry = {
                            onEvent(NewsScreenEvent.onSearchQueryChanged(state.searchQuery))
                        }
                    )
                }
            } else {
                Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        NewsScreenTopBar(
                            scrollBehavior = scrollBehavior,
                            onSearchIconClicked = {
                                onEvent(NewsScreenEvent.onSearchIconClicked)
                                coroutineScope.launch {
                                    delay(500)
                                    focusRequester.requestFocus()
                                }
                            }
                        )
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                            .background(Color.Black)
                    ) {
                        CategoryTabRow(
                            pagerState = horizontalPagerState,
                            categories = categories,
                            onTabSelected = { index ->
                                coroutineScope.launch {
                                    horizontalPagerState.animateScrollToPage(
                                        index
                                    )
                                }
                            }
                        )
                        HorizontalPager(
                            pageSize = PageSize.Fill,
                            state = horizontalPagerState,
                        ) {
                            NewsArticleList(
                                navController,
                                state = state,
                                onCardClicked = { article ->
                                    shouldShowBottomSheet = true
                                    onEvent(NewsScreenEvent.onNewsCardClicked(article = article))
                                },
                                onRetry = {
                                    onEvent(NewsScreenEvent.onCategoryChanged(state.category))
                                }
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun NewsArticleList(
    navController: NavController,
    state: NewsState,
    onCardClicked: (Article) -> Unit,
    onRetry: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(state.articles) { article ->
            NewsArticleCard(
                article = article,
                onCardClicked = onCardClicked
            )
        }
    }
    val animatable = remember {
        Animatable(0.2f)
    }
    LaunchedEffect(key1 = true) {
        animatable.animateTo(0.5f, tween(100, easing = LinearEasing))
        // you can tweak out and customize these animations.
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }
        if (state.error != null) {
            RetryContent(error = state.error, onRetry = onRetry)
        }
    }
}