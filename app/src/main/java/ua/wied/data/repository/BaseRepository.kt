package ua.wied.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import ua.wied.domain.models.exceptions.NetworkException
import java.io.IOException
import java.lang.Exception

abstract class BaseRepository {

    protected fun <T, R> handleGETApiCall(
        apiCall: suspend () -> Response<T>,
        transform: (T) -> R
    ): Flow<Result<R>> = flow {
        try {
            val response = apiCall()

            if (response.isSuccessful) {
                val responseBody = response.body()

                responseBody?.let { data ->
                    emit(Result.success(transform(data)))
                }

            } else {
                val exception = when (response.code()) {
                    400 -> NetworkException.BadRequestException
                    401 -> NetworkException.UnauthorizedException
                    404 -> NetworkException.PageNotFoundException
                    else -> NetworkException.UnknownErrorException
                }
                emit(Result.failure(exception))
            }

        } catch (e: IOException) {
            Log.d("TAG", e.message!!)
            emit(Result.failure(NetworkException.NoConnectionException))
        } catch (e: Exception) {
            Log.d("TAG", e.message!!)
            emit(Result.failure(NetworkException.UnknownErrorException))
        }
    }

}

