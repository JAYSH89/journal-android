package nl.jaysh.journal.core.domain.repository

import arrow.core.Either
import nl.jaysh.journal.core.domain.model.DataError
import nl.jaysh.journal.core.domain.model.WeightMeasurement
import nl.jaysh.journal.core.domain.model.food.Food

interface JournalRepository {
    suspend fun getWeight(): Either<DataError, List<WeightMeasurement>>
    suspend fun deleteWeightMeasurement(weightMeasurement: WeightMeasurement): Either<DataError, Unit>
    suspend fun saveWeightMeasurement(weight: Double): Either<DataError, WeightMeasurement>
    suspend fun getFood(): Either<DataError, List<Food>>
    suspend fun saveFood(food: Food): Either<DataError, Food>
    suspend fun deleteFood(id: String): Either<DataError, Unit>
}
