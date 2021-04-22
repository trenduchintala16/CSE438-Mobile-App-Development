package com.example.cse438_rest_studio.Network

import com.example.cse438_rest_studio.data.Brewery
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//interface to denote different calls with arguements
interface BreweryInterface {

    //search by parameters
    @GET("breweries/search")
    suspend fun getBreweriesBySearch(@Query("query") query: String)
            : Response<List<Brewery>>;

    @GET("breweries")
    suspend fun getBreweriesByCity(@Query("by_city") by_city: String)
            : Response<List<Brewery>>;

}