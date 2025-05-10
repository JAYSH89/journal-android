package nl.jaysh.journal.core.domain.usecase.weightmanagement

import nl.jaysh.journal.core.domain.model.WeightMeasurement
import nl.jaysh.journal.core.domain.repository.JournalRepository
import javax.inject.Inject

class DeleteWeightUseCase @Inject constructor(private val repository: JournalRepository) {
    suspend operator fun invoke(weightMeasurement: WeightMeasurement) =
        repository.deleteWeightMeasurement(weightMeasurement)
}
