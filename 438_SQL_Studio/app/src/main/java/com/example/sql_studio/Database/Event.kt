package com.example.sql_studio.Database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//create the sql table
@Entity(tableName = "event_table")
data class Event(@PrimaryKey @ColumnInfo(name = "event") @NonNull val event: String)