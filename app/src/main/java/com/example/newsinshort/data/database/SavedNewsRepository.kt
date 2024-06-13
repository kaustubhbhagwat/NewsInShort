package com.example.newsinshort.data.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.newsinshort.data.database.model.SavedArticle
import javax.inject.Inject

class SavedNewsRepository @Inject constructor(private val savedNewsDao: SavedNewsDao){

    @WorkerThread
    suspend fun insertSavedNews(article: SavedArticle){
        savedNewsDao.insertSavedNews(article)
    }

    fun getSavedNews(): LiveData<List<SavedArticle>>{
        return savedNewsDao.getAllArticles()
    }
}