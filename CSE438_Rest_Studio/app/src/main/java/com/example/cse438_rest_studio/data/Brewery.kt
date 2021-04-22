package com.example.cse438_rest_studio.data

//brewery object for ui and Moshi/Retrofit
data class Brewery(
    val id: String,
    val name: String,
    val type: String,
    val street: String,
    val city: String,
    val state: String,
    val postal_code: String,
    val country: String,
    val longitude: String,
    val latitude: String,
    val phone: String,
    val website_url: String,
    val updated_at: String,
    val tag_list : Array<String>
)