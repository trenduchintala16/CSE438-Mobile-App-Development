package com.example.sql_studio.Fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sql_studio.Adapter.EventListAdapter
import com.example.sql_studio.Database.Event
import com.example.sql_studio.EventViewModel
import com.example.sql_studio.R
import kotlinx.android.synthetic.main.fragment_view.*

class ViewFragment : Fragment() {
    private var  eventList: ArrayList<Event> = ArrayList<Event>()
    private var eventViewModel : EventViewModel? = null
    public lateinit  var clearButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set views
        clearButton = clear_button

        //set recycler view
        val recyclerView = event_recycler_view
        val adapter = EventListAdapter(eventList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.context)

        //set view model
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        //observe the allEvents LiveData
        eventViewModel!!.allEvents.observe(this, Observer { events ->
            // Update the cached copy of the words in the adapter.
            eventList.clear()
            eventList.addAll(events)
            adapter.notifyDataSetChanged()
        })

        //set clear button
        clearButton.setOnClickListener{
            println("clear run")
            //clear events
            eventViewModel!!.clear()
        }

    }
}
