package nl.jaysh.journal.core.domain.usecase.weightmanagement

import nl.jaysh.journal.core.domain.repository.JournalRepository
import javax.inject.Inject

class SaveWeightUseCase @Inject constructor(private val repository: JournalRepository) {
    suspend operator fun invoke(weight: Double) = repository.saveWeightMeasurement(weight)
}
