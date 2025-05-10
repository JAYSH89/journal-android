package nl.jaysh.journal.core.domain.usecase.authentication

import nl.jaysh.journal.core.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.register(email, password)
}