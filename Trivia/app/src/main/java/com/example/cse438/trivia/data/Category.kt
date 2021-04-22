package com.example.cse438.trivia.data

data class Category(
    val id: Int,
    val name: String
)

data class CategoryPayload (
    val trivia_categories: List<Category>
)