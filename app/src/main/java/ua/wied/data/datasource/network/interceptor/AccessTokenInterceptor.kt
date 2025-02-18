package ua.wied.data.datasource.network.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import ua.wied.data.NetworkKeys.HEADER_AUTHORIZATION
import ua.wied.data.NetworkKeys.TOKEN_TYPE
import ua.wied.domain.repository.JwtTokenManager
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private var tokenManager: JwtTokenManager
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getAccessJwt()
        }
        val request = chain.request().newBuilder()
            .addHeader(HEADER_AUTHORIZATION, "$TOKEN_TYPE $token")
            .build()
        return chain.proceed(request)
    }
}