package com.example.cse438_rest_studio.Network

import androidx.lifecycle.MutableLiveData
import com.example.cse438_rest_studio.data.Brewery
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response

class BreweryRepository {
    //get the instance of retrofit
    val service = ApiClient.makeRetrofitService()

    //searches for breweries based on string value
    fun getBreweriesBySearch(resBody : MutableLiveData<List<Brewery>>, param : String, byCity: Boolean){
        //set the coroutine on a background thread
        CoroutineScope(Dispatchers.IO).launch {
            lateinit var response: Response<List<Brewery>>
            if(byCity) {
               response =  service.getBreweriesByCity(param)
            } else {
                response = service.getBreweriesBySearch(param)
            }
            //when the coroutine finishes
            withContext(Dispatchers.Main){
                try{
                    //success case
                    if(response.isSuccessful){
                        println(response.body()?.size.toString() + " is the size")
                        resBody.value = response.body()

                    } else{
                        //response error
                        println("HTTP error")
                    }
                }catch (e: HttpException) {
                    //http exception
                    println("HTTP Exception")
                } catch (e: Throwable) {
                    //error
                    println("Error")
                }
            }
        }
    }
}