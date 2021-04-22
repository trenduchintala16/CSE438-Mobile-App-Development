package com.example.cse438_rest_studio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cse438_rest_studio.Adapter.BreweryListAdapter
import com.example.cse438_rest_studio.Network.ApiClient
import com.example.cse438_rest_studio.data.Brewery

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: BreweryViewModel
    lateinit var searchButton: SearchView
    lateinit var searchBox: EditText

    var breweryList: ArrayList<Brewery> = ArrayList<Brewery>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set intial variables
        searchBox = findViewById<EditText>(R.id.search_box)
        searchButton = findViewById<SearchView>(R.id.search_button)
        viewModel = ViewModelProviders.of(this).get(BreweryViewModel::class.java)


        //set recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val adapter = BreweryListAdapter(breweryList as ArrayList<Brewery>)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //observe the allEvents LiveData
        viewModel!!.breweryList.observe(this, Observer { breweries ->
            // Update the cached copy of the words in the adapter.
            breweryList.clear()
            breweryList.addAll(breweries)
            adapter.notifyDataSetChanged()
        })

        //autofill the recycler view on creation
        viewModel.getBreweriesByCity("saint louis")

        //click listener for when search button is pressed from edit text
        searchBox.setOnEditorActionListener() { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //your code here
                val input: String = searchBox.text.toString()
                viewModel!!.getBreweriesBySearch(input)
                true
            }
            false


        }
    }


}
