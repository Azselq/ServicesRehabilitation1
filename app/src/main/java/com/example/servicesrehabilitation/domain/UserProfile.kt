package com.example.servicesrehabilitation.domain

import com.example.servicesrehabilitation.R

data class UserProfile(
    val name: String = "Иван Иванов",
    val phoneNumber: String= "+1234567890",
    val email: String = "ivan@example.com",
    val photo: Int  = R.drawable.icon
)