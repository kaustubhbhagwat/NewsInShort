package com.example.newsinshort.data.api

import com.example.newsinshort.data.entity.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    //https://newsapi.org/v2/top-headlines?country=us&apiKey=f54f8fde71074804af445de8bc1a903c

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("category") category: String,
        @Query("country") country: String = "in",
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    @GET("everything")
    suspend fun searchForNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): NewsResponse

    companion object {
        const val API_KEY = "f54f8fde71074804af445de8bc1a903c"
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}