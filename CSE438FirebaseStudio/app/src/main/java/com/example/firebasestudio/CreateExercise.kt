package com.example.firebasestudio

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import kotlinx.android.synthetic.main.activity_create_exercise.*
import java.util.*


class CreateExercise : AppCompatActivity() {

    private lateinit var db : FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_exercise)

        //todo: create an instance of the firebase database
        db = FirebaseFirestore.getInstance()
        val settings = FirebaseFirestoreSettings.Builder()
            .setTimestampsInSnapshotsEnabled(true)
            .build()
        db.firestoreSettings = settings
    }

    override fun onStart() {
        super.onStart()

        create_exercise_button.setOnClickListener {
            //todo: create an exercise object and store it in the database
            if(exercise_name_input.text.toString() != "" &&
            exericse_reps_input.text.toString() != "" &&
            exercise_sets_input.text.toString() != "" &&
            exercise_weight_input.text.toString() != "" &&
            exercise_difficulty_input.text.toString() != "" &&
            exercise_date_input.text.toString() != ""){
                //create a new exercise
                val exercise = Exercise(
                    exercise_name_input.text.toString(),
                    exericse_reps_input.text.toString().toInt(),
                    exercise_sets_input.text.toString().toInt(),
                    exercise_weight_input.text.toString().toDouble(),
                    exercise_difficulty_input.text.toString().toInt(),
                    exercise_date_input.text.toString()
                )

                //store values for the database
                val exerciseMap: MutableMap<String, Any> = HashMap()
                exerciseMap["name"] = exercise.name
                exerciseMap["reps"] = exercise.numReps
                exerciseMap["sets"] = exercise.numSets
                exerciseMap["weight"] = exercise.weight
                exerciseMap["difficulty"] = exercise.difficulty
                exerciseMap["date"] = exercise.date


                // Add a new document with a generated ID
                db.collection("exercises")
                    .add(exerciseMap)
                    .addOnSuccessListener(OnSuccessListener<DocumentReference> { documentReference ->
                        Toast.makeText(this, "Exercise created", Toast.LENGTH_LONG)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    })
                    .addOnFailureListener(OnFailureListener { e ->
                        Toast.makeText(this, "Failed to insert data!", Toast.LENGTH_LONG)
                    })

                }else{
                Toast.makeText(this, "Values cannot be null!", Toast.LENGTH_LONG)
            }

        }
    }
}
