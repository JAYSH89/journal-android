package nl.jaysh.journal.core.domain.usecase.food

import nl.jaysh.journal.core.domain.repository.JournalRepository
import javax.inject.Inject

class DeleteFoodUseCase @Inject constructor(private val repository: JournalRepository) {
    suspend operator fun invoke(id: String) = repository.deleteFood(id)
}
