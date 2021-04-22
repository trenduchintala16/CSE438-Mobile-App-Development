package com.example.studio2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_change.*

class ChangeActivity : AppCompatActivity() {

    var total : Int = 0
    var isDeposit : Boolean = true
    public lateinit var changeButton : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change)

        //set view
        changeButton = switch_button

        //set intial fragment
        val fragment = DepositFragment()
        var bundle = Bundle()
        bundle.putInt("total", total)
        fragment.arguments = bundle
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contaner, fragment)
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()

        //store the intent value
        var bundle :Bundle ?=intent.extras
        total = bundle!!.getInt("total") // 1

        //toggle fragments with the switch button
        changeButton.setOnClickListener {
            if(isDeposit){
                val fragment = WithdrawFragement()
                var bundle = Bundle()
                bundle.putInt("total", total)
                fragment.arguments = bundle
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.contaner, fragment)
                transaction.commit()
            }else{
                val fragment = DepositFragment()
                var bundle = Bundle()
                bundle.putInt("total", total)
                fragment.arguments = bundle
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.contaner, fragment)
                transaction.commit()
            }
            isDeposit = !isDeposit
        }

    }
}
