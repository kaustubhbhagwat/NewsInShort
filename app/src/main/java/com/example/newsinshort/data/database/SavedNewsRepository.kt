package com.example.newsinshort.data.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.newsinshort.data.database.entities.Article
//import com.example.newsinshort.data.database.entities.SavedNews
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedNewsRepository @Inject constructor(private val savedNewsDao: SavedNewsDao){

    @WorkerThread
    suspend fun insertSavedNews(article: Article){
        savedNewsDao.insertSavedNews(article)
    }

    fun getSavedNews(): LiveData<List<Article>>{
        return savedNewsDao.getAllArticles()
    }
}