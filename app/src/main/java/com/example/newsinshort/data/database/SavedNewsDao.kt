package com.example.newsinshort.data.database

import androidx.room.Dao
import androidx.room.Insert
import com.example.newsinshort.data.database.entities.SavedNews

@Dao
interface SavedNewsDao{

    @Insert
    suspend fun insertSavedNews(savedNews: SavedNews)
}