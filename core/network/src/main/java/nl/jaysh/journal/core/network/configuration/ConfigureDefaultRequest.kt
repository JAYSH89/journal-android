package nl.jaysh.journal.core.network.configuration

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType.Application
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import nl.jaysh.journal.core.network.BuildConfig.BASE_URL

internal fun HttpClientConfig<AndroidEngineConfig>.configureDefaultRequest() = defaultRequest {
    url {
        protocol = URLProtocol.HTTPS
        host = BASE_URL
    }
    contentType(Application.Json)
}
