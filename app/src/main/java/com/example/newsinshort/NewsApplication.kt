package com.example.newsinshort

import android.app.Application
import android.util.Log
import com.example.newsinshort.data.database.NewsDatabase
import com.example.newsinshort.data.database.SavedNewsRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
public class NewsApplication:  Application() {

    private val database by lazy { NewsDatabase.getDatabase(this@NewsApplication) }

    val repository by lazy { SavedNewsRepository(database.savedNewsDao()) }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"Application Class OnCreate")
    }

    companion object{
        const val TAG = "NewsApplication"
    }
}