package com.example.cse438_rest_studio.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cse438_rest_studio.R
import com.example.cse438_rest_studio.data.Brewery


//define the binding for the view holder
class BreweryViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.my_brewery_item, parent, false)) {
    private val breweryNameView: TextView

    init {
        breweryNameView = itemView.findViewById(R.id.my_brewery_name)
    }

    fun bind(brewery: Brewery) {
        breweryNameView?.text = brewery.name
    }

}


//define the adapter for the recycler view
class BreweryListAdapter(private val list: ArrayList<Brewery>)
    : RecyclerView.Adapter<BreweryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BreweryViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: BreweryViewHolder, position: Int) {
        val brewery: Brewery = list[position]
        holder.bind(brewery)
    }

    override fun getItemCount(): Int = list.size

}