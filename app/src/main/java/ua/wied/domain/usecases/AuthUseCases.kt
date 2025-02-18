package ua.wied.domain.usecases

import android.content.Context
import ua.wied.domain.models.auth.AuthResult
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest
import ua.wied.domain.repository.AuthRepository
import ua.wied.domain.repository.JwtTokenManager
import javax.inject.Inject

//SIGN IN/UP
class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(request: SignInRequest): AuthResult = repository.signIn(request)
}

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(request: SignUpRequest): AuthResult = repository.signUp(request)
}

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(context: Context) = repository.logout(context)
}

class DeleteAccountUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(context: Context) = repository.deleteAccount(context)
}

//JWT
class SaveAccessJwtUseCase @Inject constructor(
    private val tokenManager: JwtTokenManager
) {
    suspend operator fun invoke(token: String) = tokenManager.saveAccessJwt(token)
}

class GetAccessJwtUseCase @Inject constructor(
    private val tokenManager: JwtTokenManager
) {
    suspend operator fun invoke(): String? = tokenManager.getAccessJwt()
}

class ClearAllTokensUseCase @Inject constructor(
    private val tokenManager: JwtTokenManager
) {
    suspend operator fun invoke() = tokenManager.clearAllTokens()
}