package com.example.newsinshort.data.datasource

import com.example.newsinshort.data.database.entities.SavedNews
import retrofit2.Response

interface NewsDataSource {
   suspend fun getNewsHeadline(country: String): Response<SavedNews>
}