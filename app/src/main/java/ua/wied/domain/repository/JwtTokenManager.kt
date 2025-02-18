package ua.wied.domain.repository

interface JwtTokenManager {
    suspend fun saveAccessJwt(token: String)
    suspend fun getAccessJwt(): String?
    suspend fun clearAllTokens()
}