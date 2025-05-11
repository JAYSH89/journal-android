package nl.jaysh.journal.core.data.repository

import nl.jaysh.journal.core.domain.model.WeightMeasurement
import nl.jaysh.journal.core.domain.model.food.Food
import nl.jaysh.journal.core.domain.repository.JournalRepository
import nl.jaysh.journal.core.network.model.food.FoodRequest
import nl.jaysh.journal.core.network.model.food.toFood
import nl.jaysh.journal.core.network.model.toWeightMeasurement
import nl.jaysh.journal.core.network.service.JournalService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JournalRepositoryImpl @Inject constructor(
    private val service: JournalService,
) : JournalRepository {

    override suspend fun getWeight() = service.getWeight().map { measurements ->
        measurements.map { it.toWeightMeasurement() }
    }

    override suspend fun saveWeightMeasurement(weight: Double) = service
        .saveWeightMeasurement(weight)
        .map { measurement -> measurement.toWeightMeasurement() }

    override suspend fun deleteWeightMeasurement(weightMeasurement: WeightMeasurement) =
        service.deleteWeight(weightMeasurement.id)

    override suspend fun getFood() = service.getFood()
        .map { list ->
            list.map { response -> response.toFood() }
        }

    override suspend fun saveFood(food: Food) = service.saveFood(FoodRequest.fromFood(food))
        .map { response -> response.toFood() }

    override suspend fun deleteFood(id: String) = service.deleteFood(id = id)
}
