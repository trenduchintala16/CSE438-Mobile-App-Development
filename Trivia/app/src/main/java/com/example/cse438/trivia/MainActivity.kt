package com.example.cse438.trivia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cse438.trivia.adapter.QuestionListAdapter
import com.example.cse438.trivia.data.Category
import com.example.cse438.trivia.data.Question
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: TriviaViewModel

    var questionList: ArrayList<Question> = ArrayList()
    var categoryList: ArrayList<Category> = ArrayList()
    var categoryNames: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(TriviaViewModel::class.java)

        var questionadapter = QuestionListAdapter(questionList)
        recyclerView.adapter = questionadapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel!!.questionList.observe(this, Observer {
            questionList.clear()
            questionList.addAll(it.results)
            questionadapter.notifyDataSetChanged()
        })

        viewModel.getQuestionsBySearch("9", "10")

        var adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryNames)
        categorySpinner.adapter = adapter
        viewModel!!.categoryList.observe(this, Observer {
            categoryList.clear()
            categoryList.addAll(it.trivia_categories)
            categoryNames.clear()
            for(c in categoryList) {
                categoryNames.add(c.name)
            }
            adapter.notifyDataSetChanged()
        })

        viewModel.getCategories()

        search_button.setOnClickListener {
            val input: String = search_box.text.toString()
            viewModel!!.getQuestionsBySearch(categoryList[categorySpinner.selectedItemPosition].id.toString(), input)
        }
    }


}
