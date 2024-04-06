package com.example.servicesrehabilitation.domain

import com.google.gson.annotations.SerializedName

data class User(val id: Int, val username: String, val name: String, val numberPhone:String, val image_url: String?)

data class AuthToken(val accessToken: String?)

data class Service(val id: Int, val user_token:String, val sevice_name: String, val perfomer_name: String, val perfomer_id : Int, val date: String )