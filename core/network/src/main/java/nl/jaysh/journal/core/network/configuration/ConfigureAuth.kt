package nl.jaysh.journal.core.network.configuration

import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.expectSuccess
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import nl.jaysh.journal.core.network.BuildConfig.BASE_URL
import nl.jaysh.journal.core.network.model.RefreshRequest
import nl.jaysh.journal.core.network.model.TokenPair
import nl.jaysh.journal.core.network.storage.TokenStorage

internal fun HttpClientConfig<AndroidEngineConfig>.configureAuth(tokenStorage: TokenStorage) {
    install(Auth) {
        bearer {
            loadTokens {
                val accessToken = tokenStorage.getAccessToken()
                val refreshToken = tokenStorage.getRefreshToken()

                if (accessToken != null && refreshToken != null) {
                    BearerTokens(accessToken, refreshToken)
                } else {
                    tokenStorage.clearTokens()
                    null
                }
            }

            refreshTokens {
                try {
                    val path = "/v1/auth/refresh"
                    val refreshToken = requireNotNull(tokenStorage.getRefreshToken())
                    val request = RefreshRequest(refreshToken)

                    val response = client.post("https://$BASE_URL$path") {
                        expectSuccess = true
                        setBody(request)
                    }

                    val tokenPair = response.body<TokenPair>()
                    tokenStorage.saveTokenPair(
                        accessToken = tokenPair.accessToken,
                        refreshToken = tokenPair.refreshToken,
                    )
                    BearerTokens(tokenPair.accessToken, tokenPair.refreshToken)
                } catch (e: Exception) {
                    tokenStorage.clearTokens()
                    null
                }
            }
        }
    }
}
