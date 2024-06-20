package com.example.newsinshort.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsinshort.data.database.model.SavedArticle
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedNewsDao {
    @Upsert
    suspend fun insertSavedNews(article: SavedArticle)

    @Upsert
    suspend fun saveNews(article: SavedArticle)

    @Query("SELECT * FROM ARTICLES ORDER BY ID")
    fun getAllArticles(): Flow<List<SavedArticle>>

    @Query("SELECT EXISTS(SELECT * FROM ARTICLES WHERE url = :url)")
    fun isRowIsExist(url : String) : Boolean

}