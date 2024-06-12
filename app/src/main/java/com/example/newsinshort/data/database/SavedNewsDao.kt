package com.example.newsinshort.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.newsinshort.data.database.entities.Article
//import com.example.newsinshort.data.database.entities.SavedNews
import kotlinx.coroutines.flow.Flow

@Dao
interface SavedNewsDao {
    @Upsert
    suspend fun insertSavedNews(article: Article)

    @Query("SELECT * FROM article ")
    fun getAllArticles(): LiveData<List<Article>>

}