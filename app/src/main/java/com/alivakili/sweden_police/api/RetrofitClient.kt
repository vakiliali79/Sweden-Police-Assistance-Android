package com.alivakili.sweden_police.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val baseUrl="https://polisen.se/api/"
    val retrofit:Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    object ApiClient {
        val apiService: Endpoint by lazy {
            retrofit.create(Endpoint::class.java)
        }
    }



}