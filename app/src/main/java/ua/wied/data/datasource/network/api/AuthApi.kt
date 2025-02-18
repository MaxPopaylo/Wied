package ua.wied.data.datasource.network.api

import com.skydoves.sandwich.ApiResponse

import retrofit2.http.Body
import retrofit2.http.POST
import ua.wied.data.datasource.network.dto.AuthResponseDto
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest

interface AuthApi {

    @POST("api/auth/signUp")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): ApiResponse<AuthResponseDto>

    @POST("api/auth/signIn")
    suspend fun signIn(
        @Body request: SignInRequest
    ): ApiResponse<AuthResponseDto>

}