package com.example.newsinshort.ui.repository

import android.util.Log
import com.example.newsinshort.data.api.NewsApi
import com.example.newsinshort.data.database.entities.Article
import com.example.newsinshort.utils.Resource
import java.lang.Exception

class NewsRepositoryImpl(
    private val newsApi: NewsApi
) : NewsRepository2 {
    override suspend fun getTopHeadlines(category: String): Resource<List<Article>> {
        return try {
            val response = newsApi.getBreakingNews(category = category)
            Log.d("Articles", response.articles.toString())
            Resource.Success(data = response.articles)
        } catch (e: Exception) {
            Resource.Error(message = "Failed to fetch News ${e.message}")
        }
    }

    override suspend fun searchForNews(query: String): Resource<List<Article>> {
        return try {
            val response = newsApi.searchForNews(query = query)
            Resource.Success(data = response.articles)
        } catch (e: Exception) {
            Resource.Error(message = "Failed to fetch News ${e.message}")
        }
    }
}