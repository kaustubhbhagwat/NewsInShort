package com.example.newsinshort.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsinshort.data.database.model.SavedArticle

@Dao
interface SavedNewsDao {
    @Upsert
    suspend fun insertSavedNews(article: SavedArticle)

    @Query("SELECT * FROM articles")
    fun getAllArticles(): LiveData<List<SavedArticle>>

}