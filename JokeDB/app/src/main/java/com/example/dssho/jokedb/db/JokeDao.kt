package com.example.dssho.jokedb.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface JokeDao {

    @Query("SELECT * FROM jokes")
    fun getJokes(): LiveData<List<Joke>>

    @Insert
    fun insert(joke: Joke)

    @Query("DELETE FROM jokes")
    fun deleteAll()
}