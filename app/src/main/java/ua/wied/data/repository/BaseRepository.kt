package ua.wied.data.repository

import android.content.Context
import android.util.Log
import android.webkit.MimeTypeMap
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import retrofit2.Response
import ua.wied.domain.models.exceptions.NetworkException
import java.io.IOException
import java.lang.Exception
import androidx.core.net.toUri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import ua.wied.domain.models.UnitFlow
import java.io.File
import ua.wied.domain.models.FlowResult

abstract class BaseRepository {

    protected fun <T, R> handleGETApiCall(
        apiCall: suspend () -> Response<T>,
        transform: (T) -> R
    ): Flow<Result<R>> = flow {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(Result.success(transform(data)))
            } ?: emit(Result.failure(NetworkException.UnknownErrorException))
        } else {
            emit(Result.failure(mapErrorStatus(response.code())))
        }
    }.catch { e ->
        Log.d("TAG", e.message ?: "Exception occurred")
        emit(Result.failure(
            when (e) {
                is IOException -> NetworkException.NoConnectionException
                else -> NetworkException.UnknownErrorException
            }
        ))
    }


    protected fun handlePOSTApiCall(
        apiCall: suspend () -> Response<Any>
    ): UnitFlow = flow {
        val response = apiCall()
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(mapErrorStatus(response.code())))
        }
    }.catch { e ->
        Log.d("TAG", e.message ?: "Exception occurred")
        emit(Result.failure(
            when(e) {
                is IOException -> NetworkException.NoConnectionException
                else -> NetworkException.UnknownErrorException
            }
        ))
    }

    protected fun <T, R> handlePOSTApiCall(
        apiCall: suspend () -> Response<T>,
        transform: (T) -> R
    ): Flow<Result<R>> = flow {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { data ->
                emit(Result.success(transform(data)))
            } ?: emit(Result.failure(NetworkException.UnknownErrorException))
        } else {
            emit(Result.failure(mapErrorStatus(response.code())))
        }
    }.catch { e ->
        Log.d("TAG", e.message ?: "Exception occurred")
        emit(Result.failure(
            when(e) {
                is IOException -> NetworkException.NoConnectionException
                else -> NetworkException.UnknownErrorException
            }
        ))
    }

    protected fun handlePUTApiCall(
        apiCall: suspend () -> Response<Any>
    ): UnitFlow = flow {
        val response = apiCall()
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(mapErrorStatus(response.code())))
        }
    }.catch { e ->
        Log.d("TAG", e.message ?: "Exception occurred")
        emit(Result.failure(
            when(e) {
                is IOException -> NetworkException.NoConnectionException
                else -> NetworkException.UnknownErrorException
            }
        ))
    }

    protected fun handleDELETEApiCall(
        apiCall: suspend () -> Response<Any>
    ): UnitFlow = flow {
        val response = apiCall()
        if (response.isSuccessful) {
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(mapErrorStatus(response.code())))
        }
    }.catch { e ->
        Log.d("TAG", e.message ?: "Exception occurred")
        emit(Result.failure(
            when(e) {
                is IOException -> NetworkException.NoConnectionException
                else -> NetworkException.UnknownErrorException
            }
        ))
    }


    private fun mapErrorStatus(code: Int): NetworkException {
        return when (code) {
            400 -> NetworkException.BadRequestException
            401 -> NetworkException.UnauthorizedException
            404 -> NetworkException.PageNotFoundException
            else -> NetworkException.UnknownErrorException
        }
    }

    suspend fun convertImagesToMultipartList(context: Context, imageUris: List<String?>, fileName: String): List<MultipartBody.Part> =
        withContext(Dispatchers.IO) {
            imageUris.mapNotNull { imageUri ->
                async {
                    urlToImageMultipart(context, imageUri, fileName)
                }
            }.awaitAll().filterNotNull()
        }

    suspend fun convertImgUrlToMultipart(context: Context, imageUri: String?, fileName: String) =
        withContext(Dispatchers.IO) {
            async {
                urlToImageMultipart(context, imageUri, fileName)
            }.await()
        }

    private fun urlToImageMultipart(context: Context, imageUri: String?, fileName: String): MultipartBody.Part? {
        if (imageUri == null) return null
        val uri = imageUri.toUri()

        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val tempFile = File.createTempFile("selectedImage", ".jpg", context.cacheDir)
                tempFile.outputStream().use { fileOut ->
                    inputStream.copyTo(fileOut)
                }

                val requestBody = tempFile.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData(fileName, tempFile.name, requestBody)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun convertVideoUrlToMultipart(context: Context, videoUri: String?, fileName: String) =
        withContext(Dispatchers.IO) {
            async {
                urlToVideoMultipart(context, videoUri, fileName)
            }.await()
        }

    private fun urlToVideoMultipart(context: Context, videoUri: String?, fileName: String): MultipartBody.Part? {
        if (videoUri == null) return null
        val uri = videoUri.toUri()

        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val mimeType = context.contentResolver.getType(uri) ?: "video/mp4"
                val extension = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(mimeType)
                    ?.let { ".$it" }
                    ?: ".mp4"

                val tempFile = File.createTempFile("selectedVideo", extension, context.cacheDir)
                tempFile.outputStream().use { fileOut ->
                    inputStream.copyTo(fileOut)
                }

                val requestBody = tempFile
                    .asRequestBody(mimeType.toMediaTypeOrNull())
                MultipartBody.Part.createFormData(fileName, tempFile.name, requestBody)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}

