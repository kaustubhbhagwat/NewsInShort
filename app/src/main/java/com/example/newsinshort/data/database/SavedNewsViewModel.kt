package com.example.newsinshort.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsinshort.data.database.model.SavedArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel @Inject constructor (private val savedNewsRepository: SavedNewsRepository): ViewModel(){


    val allSavedNews: LiveData<List<SavedArticle>> = savedNewsRepository.allSavedNews.asLiveData()

    fun insert(article: SavedArticle) = viewModelScope.launch{
        savedNewsRepository.insertSavedNews(article)
    }

    fun saveNews(article: SavedArticle) = viewModelScope.launch {
        savedNewsRepository.saveNews(article)
    }
}