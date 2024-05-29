package com.example.newsinshort.data.database

import androidx.annotation.WorkerThread
import com.example.newsinshort.data.database.entities.Article
//import com.example.newsinshort.data.database.entities.SavedNews
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedNewsRepository @Inject constructor(private val savedNewsDao: SavedNewsDao){

    @WorkerThread
    suspend fun insertSavedNews(article: Article){
        savedNewsDao.insertSavedNews(article)
    }

    val savedNewsArticles: Flow<List<Article>> = savedNewsDao.getAllArticles()
}