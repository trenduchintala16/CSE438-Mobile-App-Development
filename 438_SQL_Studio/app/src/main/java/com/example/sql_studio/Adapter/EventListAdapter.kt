package com.example.sql_studio.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sql_studio.Database.Event
import com.example.sql_studio.R

//create the view holder
class EventViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.event_layout, parent, false)) {
    private val eventText : TextView

    init {
        eventText = itemView.findViewById(R.id.event_text)
    }

    fun bind(event: Event) {
        eventText.text = event.event

    }

}

//create the listener for the recycler view
class EventListAdapter(private val list: ArrayList<Event>?)
    : RecyclerView.Adapter<EventViewHolder>() {
    private var listEvents : ArrayList<Event>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return EventViewHolder(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event: Event = listEvents!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}