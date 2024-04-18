package com.example.news.data.network

import com.example.news.data.model.newsDataResponse
import com.example.news.utils.Constants.Companion.API_KEY
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "in",
        @Query("page") page: Int,
        @Query("pageSize") pageSize : Int,
        @Query("apiKey") apiKey : String =  API_KEY
    ) : Response<newsDataResponse>
}