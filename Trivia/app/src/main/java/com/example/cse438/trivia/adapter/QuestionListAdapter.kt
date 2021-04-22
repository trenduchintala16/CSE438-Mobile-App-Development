package com.example.cse438.trivia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cse438.trivia.R
import com.example.cse438.trivia.data.Question


//define the binding for the view holder
class QuestionViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.question_item, parent, false)) {
    private val questionPrompt: TextView
    private val questionChoices: TextView

    init {
        questionPrompt = itemView.findViewById(R.id.prompt)
        questionChoices = itemView.findViewById(R.id.choices)

    }

    fun bind(question: Question) {
        questionPrompt?.text = question.question
        var choices: String = ""
        for(item in question.incorrect_answers){
            choices += item + ", "
        }
        choices += question.correct_answer
        questionChoices.text = choices
    }

}


//define the adapter for the recycler view
class QuestionListAdapter(private val list: ArrayList<Question>)
    : RecyclerView.Adapter<QuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return QuestionViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question: Question = list[position]
        holder.bind(question)
    }

    override fun getItemCount(): Int = list.size

}