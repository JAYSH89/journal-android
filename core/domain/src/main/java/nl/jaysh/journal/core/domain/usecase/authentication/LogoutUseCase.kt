package nl.jaysh.journal.core.domain.usecase.authentication

import nl.jaysh.journal.core.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {
    operator fun invoke() = repository.logout()
}
