package com.example.newsinshort.data.database

import androidx.annotation.WorkerThread
import com.example.newsinshort.data.database.entities.SavedNews
import kotlinx.coroutines.flow.Flow

class SavedNewsRepository (private val savedNewsDao: SavedNewsDao){

    @WorkerThread
    suspend fun insertSavedNews(news: SavedNews){
        savedNewsDao.insertSavedNews(news)
    }

    val savedNewsArticles: Flow<SavedNews> = savedNewsDao.getAllArticles()
}