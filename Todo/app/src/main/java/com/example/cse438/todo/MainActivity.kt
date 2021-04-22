package com.example.cse438.todo

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.enter_name.*
import kotlinx.android.synthetic.main.enter_name.view.*

class MainActivity : AppCompatActivity() {

    // List of tasks the user enters
    private var listOfTasks = ArrayList<String>()

    // Text and List views
    private var listView: ListView? = null

    private var taskNum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup functions
        getInitial()
        dialogView()
    }

    /**
     *  Assigns the list and text view objects with their respective views
     */
    private fun getInitial() {
        listView = tasks

        // Setting up the adapter using our custom built adapter
        val adapter = TaskListAdapter(this, listOfTasks)
        listView?.adapter = adapter

        adapter.notifyDataSetChanged()
    }

    /**
     * Displays the dialog box asking the user for a name
     */
    private fun dialogView() {
        // Opens the dialog view asking the user for
        val dialogView = LayoutInflater.from(this).inflate(R.layout.enter_name, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Enter your name")
        val mAlertDialog = mBuilder.show()

        // Sets an onclick listener on the dialog box button
        mAlertDialog.submitName.setOnClickListener {
            val userName = dialogView.name.text.toString()
            // If the string is empty, we do not want to accept that as an input
            if(userName != ""){
                greeting.text = "Hello, " + userName + "!"
                mAlertDialog.dismiss()
            }
        }
    }

    /**
     * Handler for adding a new task
     */
    fun addTask(view: View?){
        val taskToAdd = newTask.text.toString()
        if(taskToAdd == ""){
            val myToast = Toast.makeText(this, "Please enter valid values", Toast.LENGTH_SHORT)
            myToast.show()
        }
        else{
            listOfTasks.add(taskToAdd)
            taskNum++
            taskTotal.text = taskNum.toString()
            (listView?.adapter as? TaskListAdapter)?.notifyDataSetChanged()
        }
    }


}
