package com.example.dssho.jokedb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dssho.jokedb.JokeViewModel
import com.example.dssho.jokedb.R
import com.example.dssho.jokedb.db.Joke
import kotlinx.android.synthetic.main.fragment_add_joke_form.*
import kotlinx.android.synthetic.main.fragment_add_joke_form.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddJokeForm.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddJokeForm.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddJokeForm() : Fragment() {
    // TODO: Rename and change types of parameters
    private var jokeViewModel: JokeViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_joke_form, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        jokeViewModel = ViewModelProvider(this).get(JokeViewModel::class.java)

        view.runButton.setOnClickListener { view ->
            val j = Joke(
                setup.text.toString(),
                punchline.text.toString()
            )
            jokeViewModel!!.insert(j)

            val text = "Joke Added!"
            val duration = Toast.LENGTH_SHORT

            val toast = Toast.makeText(this.context, text, duration)
            toast.show()
        }
    }

}
