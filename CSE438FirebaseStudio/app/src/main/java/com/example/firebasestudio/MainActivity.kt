package com.example.firebasestudio

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    var exerciseList : ArrayList<Exercise> = ArrayList()
    //todo: create and store an instance of FirebaseFireStore
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //todo: set an instance of firebase
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.setFirestoreSettings(settings)
    }

    override fun onStart() {
        super.onStart()

        //set recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.exercise_list_view)
        val adapter = ExerciseListAdapter(exerciseList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //intent to the create exercise activity
        add_exercise_button.setOnClickListener{
            val intent = Intent(this, CreateExercise::class.java)
            startActivity(intent)
        }

        //todo: query for all exercises in the collection and add them to exerciseList
        db.collection("exercises")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->

                if (task.isSuccessful) {
                    exerciseList.clear()
                    for (document in task.result!!) {
                        exerciseList.add(
                            Exercise(
                                document.get("name").toString(),
                                document.get("reps").toString().toInt(),
                                document.get("sets").toString().toInt(),
                                document.get("weight").toString().toDouble(),
                                document.get("difficulty").toString().toInt(),
                                document.get("date").toString()
                            )
                        )
                    }
                    adapter.notifyDataSetChanged()
                } else {
                    println("failed to get data")
                }
            })

        search_date_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                //todo: query for documents by the chosen data and update the exerciseList
                val searchDate : String = search_date_input.text.toString()
                    db.collection("exercises")
                        .whereEqualTo("date", searchDate)
                        .get()
                        .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->

                            if (task.isSuccessful) {
                                println("search success !!!!!!!!!!!!!!!!!!")
                                exerciseList.clear()
                                for (document in task.result!!) {
                                    exerciseList.add(
                                        Exercise(
                                            document.get("name").toString(),
                                            document.get("reps").toString().toInt(),
                                            document.get("sets").toString().toInt(),
                                            document.get("weight").toString().toDouble(),
                                            document.get("difficulty").toString().toInt(),
                                            document.get("date").toString()
                                        )
                                    )
                                }
                                adapter.notifyDataSetChanged()
                            } else {
                                println("failed to get data")
                            }
                        })
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
    }
}
