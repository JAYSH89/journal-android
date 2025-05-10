package nl.jaysh.journal.core.network.configuration

import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging

internal fun HttpClientConfig<AndroidEngineConfig>.configureLogging() {
    install(Logging) {
        level = LogLevel.ALL
    }
}
