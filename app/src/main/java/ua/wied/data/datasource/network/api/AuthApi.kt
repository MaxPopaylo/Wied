package ua.wied.data.datasource.network.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import ua.wied.data.datasource.network.dto.DtoWrapper
import ua.wied.data.datasource.network.dto.UserDto
import ua.wied.domain.models.auth.SignInRequest
import ua.wied.domain.models.auth.SignUpRequest

interface AuthApi {

    @POST("api/users/registration")
    suspend fun signUp(
        @Body request: SignUpRequest
    ): Response<DtoWrapper<UserDto>>

    @POST("api/users/login")
    suspend fun signIn(
        @Body request: SignInRequest
    ): Response<DtoWrapper<UserDto>>

}