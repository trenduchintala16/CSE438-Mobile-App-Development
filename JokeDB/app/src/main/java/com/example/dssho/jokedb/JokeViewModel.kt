package com.example.dssho.jokedb

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dssho.jokedb.db.Joke
import com.example.dssho.jokedb.db.JokeRepository
import com.example.dssho.jokedb.db.JokeRoomDatabase
import kotlinx.coroutines.launch

class JokeViewModel(application: Application): AndroidViewModel(application) {
    var _jokeList: LiveData<List<Joke>> = MutableLiveData()
    private val repository: JokeRepository

    init {
        repository = JokeRepository(JokeRoomDatabase.getDatabase(application).jokeDao())
        _jokeList = repository.allJokes
    }

    fun getJokes() : LiveData<List<Joke>>{
        return _jokeList
    }

    fun insert(joke: Joke) {
        repository.insert(joke)
    }

    fun clear() {
        repository.clear()
    }



}