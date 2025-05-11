package nl.jaysh.journal.core.domain.usecase.food

import nl.jaysh.journal.core.domain.model.food.Food
import nl.jaysh.journal.core.domain.repository.JournalRepository
import javax.inject.Inject

class SaveFoodUseCase @Inject constructor(private val repository: JournalRepository) {
    suspend operator fun invoke(food: Food) = repository.saveFood(food = food)
}
