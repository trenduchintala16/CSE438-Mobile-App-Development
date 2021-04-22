package com.example.sql_studio

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.sql_studio.Database.Event
import com.example.sql_studio.Database.EventRepository
import com.example.sql_studio.Database.EventRoomDatabase
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class EventViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: EventRepository
    val allEvents: LiveData<List<Event>>

    init {
        val eventsDao = EventRoomDatabase.getDatabase(application).eventDao()
        repository = EventRepository(eventsDao)
        allEvents = repository.allEvents
    }

    //insert function on view model scope
    fun insert(event: Event) = viewModelScope.launch{
        repository.insert(event)
    }

    //delete all on view model scope
    fun clear() = viewModelScope.launch {
        repository.clear()
    }
}