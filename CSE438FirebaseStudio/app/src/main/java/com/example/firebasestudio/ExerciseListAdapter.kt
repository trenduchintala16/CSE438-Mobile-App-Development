package com.example.firebasestudio

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//define the binding for the view holder
class ExerciseViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.my_exercise_item, parent, false)) {
    private val exerciseNameView: TextView
    private val exerciseRepsView: TextView
    private val exerciseSetsView: TextView
    private val restView: TextView
    private val difficultyView: TextView
    private val dateView: TextView

    init {
        exerciseNameView = itemView.findViewById(R.id.name_display)
        exerciseRepsView = itemView.findViewById(R.id.reps_display)
        exerciseSetsView = itemView.findViewById(R.id.sets_display)
        restView = itemView.findViewById(R.id.rest_display)
        difficultyView = itemView.findViewById(R.id.difficulty_display)
        dateView = itemView.findViewById(R.id.date_display)
    }

    fun bind(exercise: Exercise) {
        exerciseNameView.text = exercise.name
        exerciseRepsView.text = "Reps: " + exercise.numReps.toString()
        exerciseSetsView.text = "Sets: " + exercise.numSets.toString()
        restView.text = "Weight: " +  exercise.weight.toString() + " Lbs"
        difficultyView.text = "Difficulty: " + exercise.difficulty.toString()
        dateView.text = "Date: " + exercise.date.toString()
    }

}


//define the adapter for the recycler view
class ExerciseListAdapter(private val list: ArrayList<Exercise>)
    : RecyclerView.Adapter<ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ExerciseViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val brewery: Exercise = list[position]
        holder.bind(brewery)
    }

    override fun getItemCount(): Int = list.size

}