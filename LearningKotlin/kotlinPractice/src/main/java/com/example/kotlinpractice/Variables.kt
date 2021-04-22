package com.example.kotlinpractice

class VariableExample {
    val immutable_string: String = "test"
    var mutable_string: String = "var"
        //private set

    var nullable_string: String? = null

    lateinit var lazy_string: String

    companion object {
        var static_string: String = "static"
    }

    fun test() {
        if(!::lazy_string.isInitialized) {
            lazy_string = "hi!"
        }
    }
}

fun main(args: Array<String>) {
    val example = VariableExample()

    //println(example.lazy_string)
    example.test()
    println(example.lazy_string)

    println(VariableExample.static_string)
}