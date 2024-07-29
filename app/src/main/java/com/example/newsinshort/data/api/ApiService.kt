package com.example.newsinshort.data.api

import com.example.newsinshort.data.database.entities.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/top-headlines")
   suspend fun getNewsHeadline(
        @Query("country") country: String,
        @Query("pageSize")pageSize:String = "50",
        @Query("apiKey") apiKey:String = "f54f8fde71074804af445de8bc1a903c",
        @Query("category") category: String = "sports"
    ): Response<NewsResponse>
}

//https://newsapi.org/v2/top-headlines?country=us&apiKey=f54f8fde71074804af445de8bc1a903c
//https://newsapi.org/v2/top-headlines?country=us&apiKey=f54f8fde71074804af445de8bc1a903c