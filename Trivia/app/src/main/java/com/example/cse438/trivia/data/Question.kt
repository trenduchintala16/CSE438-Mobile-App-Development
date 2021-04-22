package com.example.cse438.trivia.data

data class Question(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>

)

data class Payload (val response_code: Int, val results: List<Question>)