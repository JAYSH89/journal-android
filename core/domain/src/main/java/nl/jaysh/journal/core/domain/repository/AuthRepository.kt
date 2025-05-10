package nl.jaysh.journal.core.domain.repository

import arrow.core.Either
import nl.jaysh.journal.core.domain.model.DataError

interface AuthRepository {
    suspend fun register(email: String, password: String): Either<DataError, Unit>
    suspend fun login(email: String, password: String): Either<DataError, String>
    suspend fun refresh(): Either<DataError, String>
    fun logout()
}
