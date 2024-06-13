package com.example.newsinshort.data.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsinshort.data.database.model.SavedArticle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedNewsViewModel @Inject constructor (private val savedNewsRepository: SavedNewsRepository): ViewModel(){

    private val _savedNews = MutableStateFlow<List<SavedArticle>>(emptyList())
    val savedNews: StateFlow<List<SavedArticle>> = _savedNews


    fun insert(article: SavedArticle) = viewModelScope.launch{
        savedNewsRepository.insertSavedNews(article)
    }

  fun getSavedNews(){
      savedNewsRepository.getSavedNews().observeForever {
          _savedNews.value = it
      }
  }

}