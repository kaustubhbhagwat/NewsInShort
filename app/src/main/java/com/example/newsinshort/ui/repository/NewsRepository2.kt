package com.example.newsinshort.ui.repository

import com.example.newsinshort.data.database.entities.Article
import com.example.newsinshort.utils.Resource

interface NewsRepository2 {
    suspend fun getTopHeadlines(
        category: String
    ): Resource<List<Article>>


    suspend fun searchForNews(
        query: String
    ): Resource<List<Article>>
}