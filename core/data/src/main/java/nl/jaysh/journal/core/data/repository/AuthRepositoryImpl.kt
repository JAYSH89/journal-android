package nl.jaysh.journal.core.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import nl.jaysh.journal.core.common.di.IoDispatcher
import nl.jaysh.journal.core.domain.repository.AuthRepository
import nl.jaysh.journal.core.network.model.AuthRequest
import nl.jaysh.journal.core.network.service.AuthService
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
) : AuthRepository {

    override suspend fun register(email: String, password: String) = withContext(dispatcher) {
        val request = AuthRequest(email, password)
        authService.register(request)
    }

    override suspend fun login(email: String, password: String) = withContext(dispatcher) {
        val request = AuthRequest(email, password)
        authService.login(request).map { tokenPair -> tokenPair.accessToken }
    }

    override suspend fun refresh() = withContext(dispatcher) {
        authService.refresh().map { tokenPair -> tokenPair.accessToken }
    }

    override fun logout() {
        authService.logout()
    }
}
