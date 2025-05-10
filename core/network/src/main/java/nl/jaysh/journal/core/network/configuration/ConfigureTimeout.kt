package nl.jaysh.journal.core.network.configuration

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.HttpTimeout

internal fun HttpClientConfig<AndroidEngineConfig>.configureTimeout() {
    install(HttpTimeout) {
        requestTimeoutMillis = 10_000
        socketTimeoutMillis = 10_000
        connectTimeoutMillis = 5_000
    }
}
