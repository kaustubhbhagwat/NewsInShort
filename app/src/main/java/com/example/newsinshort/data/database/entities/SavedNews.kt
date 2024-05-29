package com.example.newsinshort.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "saved_news")
data class SavedNews(
    @PrimaryKey(autoGenerate = true) val savedNewsId: Int = 0,
    @ColumnInfo val status: String?,
    @ColumnInfo val totalResults: Int?,
    @ColumnInfo val articles: List<Article>
) : Parcelable

@Parcelize
@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true) val articleId: Int = 0,
    @ColumnInfo val author: String?,
    @ColumnInfo val title: String,
    @ColumnInfo val description: String?,
    @ColumnInfo var url: String,
    @ColumnInfo val urlToImage: String?,
    @ColumnInfo val publishedAt: String?,
    @ColumnInfo val content: String?,
    @Embedded val source: Source?
) : Parcelable

@Parcelize
@Entity(tableName = "source")
data class Source(
    @PrimaryKey(autoGenerate = true) val sourceId: Int?,
    @ColumnInfo val id: String?,
    @ColumnInfo val name: String?
) : Parcelable