package com.example.cse438.trivia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.cse438.trivia.data.CategoryPayload
import com.example.cse438.trivia.data.Payload
import com.example.cse438.trivia.network.TriviaRepository

class TriviaViewModel (application: Application): AndroidViewModel(application) {

    public var questionList: MutableLiveData<Payload> = MutableLiveData()
    public var categoryList: MutableLiveData<CategoryPayload> = MutableLiveData()
    public var triviaRepository: TriviaRepository = TriviaRepository()

    fun getCategories() {
        triviaRepository.getCategories(categoryList)
    }

    fun getQuestionsBySearch(category: String, amount: String) {
        triviaRepository.getQuestionsBySearch(questionList, category, amount)
    }
}