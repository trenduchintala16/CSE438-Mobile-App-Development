package com.example.cse438_rest_studio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cse438_rest_studio.Network.BreweryRepository
import com.example.cse438_rest_studio.data.Brewery


class BreweryViewModel(application: Application): AndroidViewModel(application) {

    //live data and repository to track requests
    public var breweryList: MutableLiveData<List<Brewery>> = MutableLiveData()
    public var breweryRepository : BreweryRepository = BreweryRepository()

    //request to get populare breweries
    fun getBreweriesByCity(param: String){
        breweryRepository.getBreweriesBySearch(breweryList, param, true)
    }

    //request to search for breweries
    fun getBreweriesBySearch(param: String){
        breweryRepository.getBreweriesBySearch(breweryList, param, false)
    }

}