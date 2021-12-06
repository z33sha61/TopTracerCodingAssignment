package com.example.myapplication.repository

import com.example.myapplication.api.ApiInterface
import com.example.myapplication.data.model.GiphyResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GiphyRepository {

    private val restClient: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.giphy.com/v1/gifs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiInterface::class.java)
    }

    suspend fun getGiphy(): GiphyResponse {
        return restClient.getGiphy("50", "r")
    }
}