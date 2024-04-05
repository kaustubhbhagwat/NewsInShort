package com.example.newsinshort.ui.screens.news_screen

import com.example.newsinshort.data.entity.Article

data class NewsState(
    val isLoading : Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String?= null,
    val isSearchBarVisible: Boolean = false,
    val selectedArticle: Article?= null,
    val category: String = "General",
    val searchQuery: String = ""
)