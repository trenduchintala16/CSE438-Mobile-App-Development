package com.example.studio2

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    public var total : Int = 10
    public lateinit var changeButton : Button
    public lateinit var valueDisplay : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //store the total from previous activities if it exists
        val intent = intent
        total = intent!!.getIntExtra("total", 0)
    }

    override fun onStart() {
        super.onStart()
        //set views
        changeButton = edit_balance_button
        valueDisplay = value_display

        //set total view
        valueDisplay.text = "$" + total.toString()

        //set listener for intent and total
        changeButton.setOnClickListener {
            val intent = Intent(this, ChangeActivity::class.java)
            intent.putExtra("total", total)
            startActivity(intent)
        }
    }
}
