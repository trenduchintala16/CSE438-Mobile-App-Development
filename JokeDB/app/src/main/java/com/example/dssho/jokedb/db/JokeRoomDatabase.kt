package com.example.dssho.jokedb.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Joke::class), version = 2)
public abstract class JokeRoomDatabase : RoomDatabase() {

    abstract fun jokeDao(): JokeDao

    //singleton pattern
    companion object {

        @Volatile
        private var INSTANCE: JokeRoomDatabase? = null

        fun getDatabase(context: Context) : JokeRoomDatabase {
            val temp = INSTANCE
            if(temp != null) {
                return temp
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JokeRoomDatabase::class.java,
                    "joke_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}