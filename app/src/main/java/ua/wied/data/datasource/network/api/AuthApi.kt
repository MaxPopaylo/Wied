package ua.wied.data.datasource.network.api

import retrofit2.http.Body
import retrofit2.http.POST
import ua.wied.data.datasource.network.dto.users.UserDto
import ua.wied.data.datasource.network.dto.WrappedResponse
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest

interface AuthApi {

    @POST("api/users/registration")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): WrappedResponse<UserDto>

    @POST("api/users/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): WrappedResponse<UserDto>

}