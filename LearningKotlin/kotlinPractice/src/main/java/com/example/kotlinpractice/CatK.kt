package com.example.kotlinpractice

data class CatK(val age: Int, val name: String)

fun main(args: Array<String>) {
    val franklin = CatK(12, "franklin")

    println(franklin.age)
    println(franklin)
}