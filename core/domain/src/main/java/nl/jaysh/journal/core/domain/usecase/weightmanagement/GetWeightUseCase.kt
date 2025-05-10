package nl.jaysh.journal.core.domain.usecase.weightmanagement

import nl.jaysh.journal.core.domain.repository.JournalRepository
import javax.inject.Inject

class GetWeightUseCase @Inject constructor(private val repository: JournalRepository) {

    suspend operator fun invoke() = repository.getWeight().map { list ->
        list.sortedByDescending { measurement -> measurement.measuredAt.date }
    }
}
