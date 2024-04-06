package com.example.servicesrehabilitation.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthTokenDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAuthToken(authToken: AuthToken)

    @Query("SELECT * FROM auth_token WHERE id = 1")
    suspend fun getAuthToken(): AuthToken?
    @Query("DELETE FROM auth_token WHERE id = 1")
    suspend fun deleteAuthToken()

}
