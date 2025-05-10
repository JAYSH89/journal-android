package nl.jaysh.journal.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.jaysh.journal.core.data.repository.AuthRepositoryImpl
import nl.jaysh.journal.core.data.repository.JournalRepositoryImpl
import nl.jaysh.journal.core.domain.repository.AuthRepository
import nl.jaysh.journal.core.domain.repository.JournalRepository

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindsAuthRepository(authRepository: AuthRepositoryImpl): AuthRepository

    @Binds
    fun bindsJournalRepository(journalRepository: JournalRepositoryImpl): JournalRepository
}
