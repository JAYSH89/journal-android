package nl.jaysh.journal.core.network.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import nl.jaysh.journal.core.network.configuration.configureAuth
import nl.jaysh.journal.core.network.configuration.configureContentNegotiation
import nl.jaysh.journal.core.network.configuration.configureDefaultRequest
import nl.jaysh.journal.core.network.configuration.configureLogging
import nl.jaysh.journal.core.network.configuration.configureTimeout
import nl.jaysh.journal.core.network.storage.TokenStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesTokenStorage(@ApplicationContext context: Context) = TokenStorage(context)

    @Singleton
    @Provides
    fun providesPublicHttpClient(tokenStorage: TokenStorage) = HttpClient(Android) {
        configureContentNegotiation()
        configureTimeout()
        configureDefaultRequest()
        configureLogging()
        configureAuth(tokenStorage)
    }
}
