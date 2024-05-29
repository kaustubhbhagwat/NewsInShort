package com.example.newsinshort.data.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsinshort.data.database.entities.Article
import com.example.newsinshort.data.database.entities.SavedNews
import com.example.newsinshort.data.database.entities.Source

@TypeConverters(ArticlesTypeConvertor::class)
@Database(entities = [SavedNews::class,Article::class,Source::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun savedNewsDao(): SavedNewsDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null
        fun getDatabase(context: android.content.Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
