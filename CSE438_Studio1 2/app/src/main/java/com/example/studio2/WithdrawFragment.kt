package com.example.studio2

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_withdraw.*


class WithdrawFragement : Fragment() {

    public var total = 0
    public lateinit var withdrawButton : Button
    public lateinit var withdrawValue : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = activity!!.intent
        total = intent!!.getIntExtra("total", 0)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_withdraw, container, false)
    }

    override fun onStart() {
        super.onStart()

        //set text views
        withdrawButton = withdraw_button
        withdrawValue = withdraw_value

        //set click listener
        withdrawButton.setOnClickListener {

            //set value
            var value : Int? = withdrawValue.text.toString().toInt()
            if(value != null){
                total -= value
            }

            //set intent
            val intent = Intent (activity, MainActivity::class.java)
            intent.putExtra("total", total)
            activity!!.startActivity(intent)
        }
    }




}
