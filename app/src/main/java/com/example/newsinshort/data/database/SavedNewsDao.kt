package com.example.newsinshort.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.newsinshort.data.database.entities.Article
//import com.example.newsinshort.data.database.entities.SavedNews
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedNewsDao {
    @Insert
    suspend fun insertSavedNews(article: Article)


    @Query("SELECT * FROM article ")
    fun getAllArticles(): Flow<List<Article>>
}