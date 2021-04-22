package com.example.kotlinpractice

fun main(args: Array<String>) {
    val x: Int = 7
    val y: Int = 5
    var z: Int = 0

    if (x > y) {
        z = x - y
    } else {
        z = y - x
    }

    for (a in 0..5) {
        println(a)
    }

    for (a in 10 downTo 0 step 2) {

    }

    val anArray: Array<Any> = arrayOf(
        "one",
        "two",
        "three"
    )

    for(value in anArray) {
        println(value)
    }

    for(i in anArray.indices) {
        println("${anArray[i]}")
    }
}