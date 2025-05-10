package nl.jaysh.journal.core.domain.usecase.authentication

import nl.jaysh.journal.core.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.login(email, password)
}