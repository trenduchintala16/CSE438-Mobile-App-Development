package com.example.sql_studio.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

//define the DAO for data access to the table
@Dao
interface EventDao {
    @Query("SELECT * from event_table ORDER BY event ASC")
    fun getAlphabetizedEvents(): LiveData<List<Event>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: Event)

    @Query("DELETE FROM event_table")
    fun deleteAll()
}