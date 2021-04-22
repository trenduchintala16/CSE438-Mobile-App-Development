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
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.sql_studio.Database.Event
import com.example.sql_studio.EventViewModel
import com.example.sql_studio.MainActivity

import com.example.sql_studio.R
import kotlinx.android.synthetic.main.fragment_add.*

class AddFragment : Fragment() {
    private var eventViewModel : EventViewModel? = null
    public lateinit var createEventButton : Button
    public lateinit var inputText : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set the view model
        eventViewModel = ViewModelProvider(this).get(EventViewModel::class.java)

        //set the buttons and text
        createEventButton = create_event_button
        inputText = event_text_input

        //set the create button
        createEventButton.setOnClickListener{
            println("here")
            val inputTextVal : String? = inputText.text.toString()
            if(!inputTextVal!!.equals("")){
                eventViewModel!!.insert(Event(inputTextVal as String))
                Toast.makeText(this.context, "Event created!", Toast.LENGTH_SHORT).show()
                inputText.setText("")
            }else{
                Toast.makeText(this.context, "Value cannot be null", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
