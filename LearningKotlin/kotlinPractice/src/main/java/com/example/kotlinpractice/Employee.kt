package com.example.kotlinpractice


import java.util.*


data class Employee(val EmployeeNumber: Long, var FirstName: String, var LastName: String, val BirthDate: Date, val StartDate: Date, private var Salary: Double) {
    fun raise() {
        Salary += 1000;
    }
}

fun main(args: Array<String>) {

    var Bob = Employee(1, "Bob", "Smith", Date(1990, 3, 4), Date(2020,1,3), 3000.00)
    var Steve = Employee(2, "Steve", "Johnson",  Date(1990, 3, 5), Date(2020, 1, 2), 2000.00)
    var John = Employee(3, "John", "Carrig", Date(1990, 6, 4), Date(2020,1,1), 4000.00)

    var empArray: Array<Employee> = arrayOf(
        Bob, Steve, John
    )

    for(emp in empArray){
        println(emp)
        emp.raise()
        println(emp)
    }

}

