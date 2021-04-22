package com.example.cse438.trivia.network

import com.example.cse438.trivia.data.CategoryPayload
import com.example.cse438.trivia.data.Payload
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaInterface {

    @GET("api_category.php")
    suspend fun getCategories(): Response<CategoryPayload>

    @GET("api.php")
    suspend fun getQuestionsBySearch(@Query("category") category: String, @Query("amount") amount: String) : Response<Payload>
}