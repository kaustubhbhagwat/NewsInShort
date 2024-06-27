package com.example.newsinshort.data.database

import androidx.annotation.WorkerThread
import com.example.newsinshort.data.database.model.SavedArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedNewsRepository @Inject constructor(private val savedNewsDao: SavedNewsDao){

    val allSavedNews: Flow<List<SavedArticle>> = savedNewsDao.getAllArticles()

    @WorkerThread
    suspend fun insertSavedNews(article: SavedArticle){
        savedNewsDao.insertSavedNews(article)
    }

    suspend fun saveNews(article: SavedArticle) = savedNewsDao.saveNews(article)

    fun isRowExist(url : String) = savedNewsDao.isRowIsExist(url)

    fun ifUrlExists(url: String): Boolean = savedNewsDao.ifUrlExists(url)
}