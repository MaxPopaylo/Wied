package ua.wied.domain.repository

import ua.wied.domain.models.user.User

interface UserStorage {
    suspend fun saveUser(user: User)
    suspend fun getUser(): User
    suspend fun clearUserData()
}