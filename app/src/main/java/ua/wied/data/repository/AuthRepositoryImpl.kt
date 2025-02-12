package ua.wied.data.repository

import android.content.Context
import ua.wied.domain.models.auth.AuthResult
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest
import ua.wied.domain.models.user.User
import ua.wied.domain.repository.AuthRepository
import ua.wied.domain.usecases.ClearUserDataUseCase
import ua.wied.domain.usecases.SaveUserUseCase
import java.util.UUID
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase
): AuthRepository {
    override suspend fun signIn(request: SignInRequest): AuthResult {
//        TODO("Not yet implemented")
        saveUserUseCase.invoke(User(
            id = UUID.randomUUID().toString(),
            name = "UserName",
            phone = "000000000",
            company = "CompanyName"
        ))
        return AuthResult.Success
    }

    override suspend fun signUp(request: SignUpRequest): AuthResult {
//        TODO("Not yet implemented")
        saveUserUseCase.invoke(User(
            id = UUID.randomUUID().toString(),
            name = "UserName",
            phone = "000000000",
            company = "CompanyName"
        ))
        return AuthResult.Success
    }

    override suspend fun logout(context: Context) {
//        TODO("Not yet implemented")
        clearUserDataUseCase.invoke()
    }

    override suspend fun deleteAccount(context: Context) {
//        TODO("Not yet implemented")
    }
}