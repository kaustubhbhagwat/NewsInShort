package com.example.newsinshort.data.datasource

import com.example.newsinshort.data.entity.NewsResponse
import retrofit2.Response
import retrofit2.http.Query

interface NewsDataSource {

    fun getNewsHeadline(country: String): Response<NewsResponse>
}