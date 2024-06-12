package com.example.newsinshort.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.newsinshort.data.database.entities.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SavedNewsViewModel @Inject constructor (private val savedNewsRepository: SavedNewsRepository): ViewModel(){

    private val _savedNews = MutableStateFlow<List<Article>>(emptyList())
    val savedNews: StateFlow<List<Article>> = _savedNews


    fun insert(article: Article) = viewModelScope.launch{
        savedNewsRepository.insertSavedNews(article)
    }

  fun getSavedNews(){
      savedNewsRepository.getSavedNews().observeForever {
          _savedNews.value = it
      }
  }

}