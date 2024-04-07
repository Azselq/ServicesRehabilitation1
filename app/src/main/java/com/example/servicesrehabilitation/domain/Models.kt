package com.example.servicesrehabilitation.domain

import com.google.gson.annotations.SerializedName

data class User(val id: Int, val username: String, val name: String, val numberPhone:String, val image_url: String?)

data class AuthToken(val accessToken: String?)

data class AppointmentModel(
    val user_token: String,
    val service_name: String,
    val performer_name: String,
    val performer_id: Int,
    val date: String
)

data class AppointmentResponse(
    val id: Int,
    val user_token: String,
    val service_name: String,
    val performer_name: String,
    val performer_id: Int,
    val date: String
)