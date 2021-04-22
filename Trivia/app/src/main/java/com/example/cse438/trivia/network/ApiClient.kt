package com.example.cse438.trivia.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiClient {
    const val BASE_URL = "https://opentdb.com/"

    fun makeRetrofitService(): TriviaInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(TriviaInterface::class.java)

    }
}