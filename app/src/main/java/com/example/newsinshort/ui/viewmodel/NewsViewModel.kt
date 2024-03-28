package com.example.newsinshort.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(): ViewModel(){
    val value = "value"

    init {
        Log.d(TAG,"Init Block of NewsViewModel")
    }

    fun doSomething(){
        println("Do Something")
    }

    companion object{
        const val TAG = "NewsViewModel"
    }
}