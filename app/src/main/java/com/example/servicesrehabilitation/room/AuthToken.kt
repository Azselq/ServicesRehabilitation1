package com.example.servicesrehabilitation.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "auth_token")
data class AuthToken(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1, // Используйте статический ID, если у вас будет только одна запись токена
    val token: String?
)
