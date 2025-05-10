package nl.jaysh.journal.core.network.service

import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.Either.Right
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.isSuccess
import nl.jaysh.journal.core.domain.model.DataError
import nl.jaysh.journal.core.domain.model.DataError.Network
import nl.jaysh.journal.core.network.model.AuthRequest
import nl.jaysh.journal.core.network.model.RefreshRequest
import nl.jaysh.journal.core.network.model.TokenPair
import nl.jaysh.journal.core.network.storage.TokenStorage
import javax.inject.Inject

class AuthService @Inject constructor(
    private val httpClient: HttpClient,
    private val tokenStorage: TokenStorage,
) {
    suspend fun register(authRequest: AuthRequest): Either<DataError, Unit> {
        val path = "/v1/auth/register"

        val response = httpClient.post(urlString = path) {
            setBody(authRequest)
        }

        if (response.status.isSuccess()) {
            return Right(Unit)
        }

        return Left(Network.UNKNOWN)
    }

    suspend fun login(authRequest: AuthRequest): Either<DataError, TokenPair> {
        val path = "/v1/auth/login"

        val response = httpClient.post(urlString = path) {
            setBody(authRequest)
        }

        if (response.status.isSuccess()) {
            val tokenPair = response.body<TokenPair>()
            tokenStorage.saveTokenPair(tokenPair.accessToken, tokenPair.refreshToken)
            return Right(tokenPair)
        }

        return Left(Network.UNKNOWN)
    }

    suspend fun refresh(): Either<DataError, TokenPair> {
        val refreshToken = tokenStorage.getRefreshToken() ?: return Left(Network.UNKNOWN)

        val path = "/v1/auth/refresh"
        val response = httpClient.post(urlString = path) {
            setBody(RefreshRequest(refreshToken = refreshToken))
        }

        if (response.status.isSuccess()) {
            val tokenPair = response.body<TokenPair>()
            tokenStorage.saveTokenPair(tokenPair.accessToken, tokenPair.refreshToken)
            return Right(tokenPair)
        }

        return Left(Network.UNKNOWN)
    }

    fun logout() {
        tokenStorage.clearTokens()
    }
}
