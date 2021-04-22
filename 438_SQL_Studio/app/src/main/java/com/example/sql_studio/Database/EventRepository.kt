package com.example.sql_studio.Database

import android.app.Application
import android.os.AsyncTask
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.CoroutineScope



// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class EventRepository(private val eventDao: EventDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allEvents: LiveData<List<Event>> = eventDao.getAlphabetizedEvents()

    fun insert(event: Event){
        //insertAsyncTask(eventDao!!).execute(event)
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.insert(event)
        }
    }

    fun clear(){
        //clearAsyncTask(eventDao!!).execute()
        CoroutineScope(Dispatchers.IO).launch {
            eventDao!!.deleteAll()
        }
    }


//    private class insertAsyncTask internal constructor(private val mAsyncTaskDao: EventDao) : AsyncTask<Event, Void, Void>() {
//        override fun doInBackground(vararg params: Event): Void? {
//            mAsyncTaskDao.insert(params[0])
//            return null
//        }
//    }
//
//    private class clearAsyncTask internal constructor(private val mAsyncTaskDao: EventDao): AsyncTask<Void, Void, Void>() {
//        override fun doInBackground(vararg p0: Void?): Void? {
//            mAsyncTaskDao.deleteAll()
//            return null
//        }
//    }
}