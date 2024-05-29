package com.example.newsinshort.data.datasource

import com.example.newsinshort.data.api.ApiService
import com.example.newsinshort.data.database.entities.SavedNews
import retrofit2.Response
import javax.inject.Inject

class NewsDataSourceImpl @Inject constructor(
    private val apiService: ApiService
) : NewsDataSource {
    override suspend fun getNewsHeadline(country: String): Response<SavedNews> {
        return apiService.getNewsHeadline(country)
    }
}