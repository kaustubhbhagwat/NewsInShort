package com.example.newsinshort.data.database.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "saved_news")
data class SavedNews(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>
) : Parcelable

@Parcelize
@Entity(tableName = "article")
data class Article(
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
@Entity(tableName = "source")
data class Source(
    val id: String?,
    val name: String?
) : Parcelable