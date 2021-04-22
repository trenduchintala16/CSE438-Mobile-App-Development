package com.example.dssho.jokedb.db

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JokeRepository(private val jokeDao: JokeDao) {

    val allJokes: LiveData<List<Joke>> = jokeDao.getJokes()

    fun insert(joke: Joke) {
        CoroutineScope(Dispatchers.IO).launch {
            jokeDao.insert(joke)
        }
    }

    fun clear() {
        CoroutineScope(Dispatchers.IO).launch {
            jokeDao.deleteAll()
        }
    }
}