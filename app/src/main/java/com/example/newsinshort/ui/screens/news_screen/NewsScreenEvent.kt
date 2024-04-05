package com.example.newsinshort.ui.screens.news_screen

import com.example.newsinshort.data.entity.Article

sealed class NewsScreenEvent {
    data class onNewsCardClicked(var article: Article) : NewsScreenEvent()

    data class onCategoryChanged(var cartegory : String) : NewsScreenEvent()

    data class onSearchQueryChanged (var searchQuery: String): NewsScreenEvent()

    object onSearchIconClicked : NewsScreenEvent()

    object onCloseIconClick : NewsScreenEvent()
}