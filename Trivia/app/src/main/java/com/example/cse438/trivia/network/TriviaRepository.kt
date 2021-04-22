package com.example.cse438.trivia.network

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.cse438.trivia.data.CategoryPayload
import com.example.cse438.trivia.data.Payload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class TriviaRepository {
    val service = ApiClient.makeRetrofitService()

    fun getCategories(resBody: MutableLiveData<CategoryPayload>) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCategories()
            Log.d("here", "$response")
            withContext(Dispatchers.Main) {
                tr
                    if(response.isSuccessful) {
                        resBody.value = response.body()
                    }
                } catch (e: HttpException) {
                    println("Http error")
                }
            }
        }


    fun getQuestionsBySearch(resBody: MutableLiveData<Payload>, category: String, amount: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getQuestionsBySearch(category, amount)

            withContext(Dispatchers.Main) {
                try{
                    if(response.isSuccessful) {
                        resBody.value = response.body()
                    }
                } catch (e: HttpException) {
                    println("Http error")
                }
            }
        }
    }
}