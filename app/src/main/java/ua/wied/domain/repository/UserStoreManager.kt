package ua.wied.domain.repository

import ua.wied.domain.models.user.User

interface UserStoreManager {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun updateUserData(user: User)
    suspend fun clearUserData()
}