package nl.jaysh.journal.core.network.configuration

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.AndroidEngineConfig
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging

internal fun HttpClientConfig<AndroidEngineConfig>.configureLogging() {
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                Napier.v(
                    message = message,
                    throwable = null,
                    tag = "HTTP CLIENT",
                )
            }
        }
    }.also { Napier.base(DebugAntilog()) }
}
