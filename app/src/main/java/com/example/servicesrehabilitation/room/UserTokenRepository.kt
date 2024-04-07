package com.example.servicesrehabilitation.room

class UserTokenRepository(val tokenDao: AuthTokenDao) {
    suspend fun getToken(): AuthToken? {
        return tokenDao.getAuthToken()
    }
}