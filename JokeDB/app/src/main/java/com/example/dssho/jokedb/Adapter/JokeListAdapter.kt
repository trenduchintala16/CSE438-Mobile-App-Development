package com.example.dssho.jokedb.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dssho.jokedb.R
import com.example.dssho.jokedb.db.Joke

//create the view holder
class JokeViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.joke_item, parent, false)) {
    private val setup : TextView
    private val punchline : TextView

    init {
        setup = itemView.findViewById(R.id.setup)
        punchline = itemView.findViewById(R.id.punchline)
    }

    fun bind(joke: Joke) {
        setup.text = joke.setup
        punchline.text = joke.punchline

    }

}

//create the listener for the recycler view
class JokeListAdapter(private val list: ArrayList<Joke>?)
    : RecyclerView.Adapter<JokeViewHolder>() {
    private var listEvents : ArrayList<Joke>? = list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return JokeViewHolder(inflater, parent)
    }

    //bind the object
    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val event: Joke = listEvents!!.get(position)
        holder.bind(event)
    }

    //set the count
    override fun getItemCount(): Int = list!!.size

}