package com.example.dssho.jokedb.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes")
data class Joke(
    @ColumnInfo(name = "setup")
    val setup: String,
    @ColumnInfo(name = "punchline")
    val punchline: String
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}