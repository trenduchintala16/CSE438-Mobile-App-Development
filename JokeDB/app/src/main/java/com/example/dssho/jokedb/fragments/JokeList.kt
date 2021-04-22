package com.example.dssho.jokedb.fragments

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dssho.jokedb.JokeViewModel
import com.example.dssho.jokedb.R
import com.example.dssho.jokedb.db.Joke
import com.example.dssho.jokedb.Adapter.JokeListAdapter
import kotlinx.android.synthetic.main.fragment_joke_list.*


class JokeList() : Fragment() {

    private lateinit var viewModel: JokeViewModel

    private var jokeList: ArrayList<Joke> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_joke_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        var adapter = JokeListAdapter(jokeList)
        joke_recycler_view.adapter = adapter
        joke_recycler_view.layoutManager = LinearLayoutManager(this.context)
        joke_recycler_view.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        viewModel = ViewModelProvider(this).get(JokeViewModel::class.java)

        viewModel!!._jokeList.observe(this, Observer { jokes ->
            // Update the cached copy of the words in the adapter.
            jokeList.clear()
            jokeList.addAll(jokes)
            adapter.notifyDataSetChanged()
        })

        clear_button.setOnClickListener {
            viewModel.clear()
            jokeList.clear()
        }
    }
}
