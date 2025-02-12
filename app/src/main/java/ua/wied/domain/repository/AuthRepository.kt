package ua.wied.domain.repository

import android.content.Context
import ua.wied.domain.models.auth.AuthResult
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest

interface AuthRepository {
    suspend fun signIn(request: SignInRequest): AuthResult
    suspend fun signUp(request: SignUpRequest): AuthResult
    suspend fun logout(context: Context)
    suspend fun deleteAccount(context: Context)
}