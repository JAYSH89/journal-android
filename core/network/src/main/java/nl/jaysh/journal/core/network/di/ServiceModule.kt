package nl.jaysh.journal.core.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import nl.jaysh.journal.core.network.service.AuthService
import nl.jaysh.journal.core.network.service.JournalService
import nl.jaysh.journal.core.network.storage.TokenStorage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Provides
    @Singleton
    fun providesAuthService(
        httpClient: HttpClient,
        tokenStorage: TokenStorage,
    ) = AuthService(
        httpClient = httpClient,
        tokenStorage = tokenStorage,
    )

    @Provides
    @Singleton
    fun providesJournalService(httpClient: HttpClient) = JournalService(httpClient = httpClient)
}
