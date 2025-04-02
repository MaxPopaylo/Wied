package ua.wied.data.repository

import android.content.Context
import android.content.Intent
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.wied.R
import ua.wied.data.datasource.network.api.AuthApi
import ua.wied.data.datasource.network.dto.ErrorResponse
import ua.wied.domain.models.auth.AuthResult
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest
import ua.wied.domain.models.user.User
import ua.wied.domain.repository.AuthRepository
import ua.wied.domain.usecases.ClearAllTokensUseCase
import ua.wied.domain.usecases.ClearUserDataUseCase
import ua.wied.domain.usecases.SaveAccessJwtUseCase
import ua.wied.domain.usecases.SaveUserUseCase
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val saveUserUseCase: SaveUserUseCase,
    private val saveJwtUseCase: SaveAccessJwtUseCase,
    private val clearUserDataUseCase: ClearUserDataUseCase,
    private val clearAllTokensUseCase: ClearAllTokensUseCase,
    private val api: AuthApi,
    moshi: Moshi
): AuthRepository {
    private val jsonAdapter = moshi.adapter(ErrorResponse::class.java)

    override suspend fun signIn(request: SignInRequest): AuthResult {
        return try {
            val response = api.signIn(request)

            if (response.isSuccessful) {
                val responseBody = response.body()

                responseBody?.let { data ->
                    data.let {
                        saveUserUseCase.invoke(User(
                            id = it.data.id,
                            login = it.data.login,
                            name = it.data.name,
                            phone = it.data.phone,
                            email = it.data.email ?: "",
                            company = it.data.company,
                            info = it.data.info ?: "",
                            role = it.data.role
                        ))
                        saveJwtUseCase.invoke(it.data.token)
                    }
                }

                AuthResult.Success
            } else {
                val errorBody = response.errorBody()
                val errorResponse = errorBody?.let { jsonAdapter.fromJson(it.string()) }
                val msg = errorResponse?.detail ?: "Unknown error"

                 when {
                    msg.contains("Wrong login or password") -> AuthResult.Error(R.string.error_incorrect_login_or_password)
                    else -> AuthResult.Error(R.string.error)
                }
            }
        } catch (exception: IOException) {
            AuthResult.Error(R.string.error)
        } catch (exception: Exception) {
            AuthResult.Error(R.string.error)
        }
    }

    override suspend fun signUp(request: SignUpRequest): AuthResult {
        return try {
            val response = api.signUp(request)

            if (response.isSuccessful) {
                val responseBody = response.body()

                responseBody?.let { data ->
                    data.let {
                        saveUserUseCase.invoke(User(
                            id = it.data.id,
                            login = it.data.login,
                            name = it.data.name,
                            phone = it.data.phone,
                            email = it.data.email ?: "",
                            company = it.data.company,
                            info = it.data.info ?: "",
                            role = it.data.role
                        ))
                        saveJwtUseCase.invoke(it.data.token)
                    }
                }

                AuthResult.Success
            } else {
                val errorBody = response.errorBody()
                val errorResponse = errorBody?.let { jsonAdapter.fromJson(it.string()) }
                val msg = errorResponse?.detail ?: "Unknown error"

                when {
                    msg.contains("already exists") -> {
                        when {
                            msg.contains("User with login") -> AuthResult.Error(R.string.error_uniq_login)
                            msg.contains("Company") -> AuthResult.Error(R.string.error_uniq_company)
                            else -> AuthResult.Error(R.string.error)
                        }
                    }
                    else -> AuthResult.Error(R.string.error)
                }
            }
        } catch (exception: IOException) {
            AuthResult.Error(R.string.error)
        } catch (exception: Exception) {
            AuthResult.Error(R.string.error)
        }
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