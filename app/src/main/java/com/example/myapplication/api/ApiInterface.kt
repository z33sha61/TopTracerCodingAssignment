package com.example.myapplication.api

import com.example.myapplication.data.model.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("trending?api_key=i8DR6tSDykQMFDS6GkFRdDClyXUIwA5i")
    suspend fun getGiphy(
        @Query("limit") limit: String,
        @Query("rating") rating: String
    ): GiphyResponse

}