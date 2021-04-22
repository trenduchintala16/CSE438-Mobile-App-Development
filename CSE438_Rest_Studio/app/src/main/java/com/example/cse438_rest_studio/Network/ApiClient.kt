package com.example.cse438_rest_studio.Network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

//base retrofit client to define settings and url
object ApiClient {
    const val BASE_URL = "https://api.openbrewerydb.org/"

    fun makeRetrofitService(): BreweryInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(BreweryInterface::class.java)
    }
}