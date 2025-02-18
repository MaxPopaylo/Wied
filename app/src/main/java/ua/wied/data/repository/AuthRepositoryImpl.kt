package ua.wied.data.repository

import android.content.Context
import android.content.Intent
import com.skydoves.sandwich.retrofit.statusCode
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnFailure
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.wied.R
import ua.wied.data.datasource.network.api.AuthApi
import ua.wied.domain.models.auth.AuthResult
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest
import ua.wied.domain.repository.AuthRepository
import ua.wied.domain.usecases.ClearAllTokensUseCase
import ua.wied.domain.usecases.ClearUserDataUseCase
import ua.wied.domain.usecases.SaveAccessJwtUseCase
import ua.wied.domain.usecases.SaveUserUseCase
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
    private val saveJwtUseCase: SaveAccessJwtUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase,
    private val clearAllTokensUseCase: ClearAllTokensUseCase,
    private val context: Context,
    private val api: AuthApi
): AuthRepository {
    override suspend fun signIn(request: SignInRequest): AuthResult {
        var result: AuthResult = AuthResult.Error(R.string.error)
        api.signIn(request)
            .suspendOnSuccess {
                data.let {
                    saveUserUseCase.invoke(it.user)
                    saveJwtUseCase.invoke(it.token)
                }
                result = AuthResult.Success
            }
            .suspendOnError {
                when(statusCode) {
                    
                    else -> AuthResult.Error(R.string.error)
                }
                result = AuthResult.Error(R.string.error)
            }
            .suspendOnFailure { AuthResult.Error(R.string.error)  }

        return result
    }

    override suspend fun signUp(request: SignUpRequest): AuthResult {
        var result: AuthResult = AuthResult.Error(R.string.error)
        api.signUp(request)
            .suspendOnSuccess {
                data.let {
                    saveUserUseCase.invoke(it.user)
                    saveJwtUseCase.invoke(it.token)
                }
                result = AuthResult.Success
            }
            .suspendOnError {
                when(statusCode) {

                    else -> AuthResult.Error(R.string.error)
                }
                result = AuthResult.Error(R.string.error)
            }
            .suspendOnFailure { AuthResult.Error(R.string.error)  }

        return result
    }

    override suspend fun logout(context: Context) {
        withContext(Dispatchers.IO) {
            clearUserDataUseCase.invoke()
            clearAllTokensUseCase.invoke()
        }

        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        val mainIntent = Intent.makeRestartActivityTask(intent?.component)
        context.startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }

    override suspend fun deleteAccount(context: Context) {
//        TODO("Not yet implemented")
    }
}