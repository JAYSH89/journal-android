package nl.jaysh.journal.core.domain.usecase.authentication

import nl.jaysh.journal.core.domain.repository.AuthRepository
import javax.inject.Inject

class RefreshUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke() = authRepository.refresh()
}
