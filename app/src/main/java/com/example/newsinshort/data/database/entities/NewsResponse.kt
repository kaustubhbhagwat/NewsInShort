package com.example.newsinshort.data.database.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>
) : Parcelable

@Parcelize
data class Article(
    val articleId: Int = 0,
    val author: String?,
    val title: String,
    val description: String?,
    var url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    val source: Source?
) : Parcelable

@Parcelize
data class Source(
    val id: String?,
    val name: String?
) : Parcelable