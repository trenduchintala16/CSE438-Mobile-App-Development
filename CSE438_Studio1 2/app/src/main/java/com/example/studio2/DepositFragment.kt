package com.example.studio2

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_deposit.*

class DepositFragment : Fragment() {
    public var total = 0
    public lateinit var depositButton : Button
    public lateinit var depositValue : TextView

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
        return inflater.inflate(R.layout.fragment_deposit, container, false)
    }

    override fun onStart() {
        super.onStart()

        //set views
        depositButton = deposit_button
        depositValue = deposit_value

        //set listener for deposite button
        depositButton.setOnClickListener {
            var value : Int? = depositValue.text.toString().toInt()
            if(value != null){
                total += value
            }

            //set value for intent to add to total
            val intent = Intent (activity, MainActivity::class.java)
            intent.putExtra("total", total)
            activity!!.startActivity(intent)
        }
    }

}
