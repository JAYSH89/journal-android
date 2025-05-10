package nl.jaysh.journal.core.domain.repository

import arrow.core.Either
import nl.jaysh.journal.core.domain.model.DataError
import nl.jaysh.journal.core.domain.model.WeightMeasurement

interface JournalRepository {
    suspend fun getWeight(): Either<DataError, List<WeightMeasurement>>
    suspend fun deleteWeightMeasurement(weightMeasurement: WeightMeasurement): Either<DataError, Unit>
    suspend fun saveWeightMeasurement(weight: Double): Either<DataError, WeightMeasurement>
    suspend fun getFood(): Either<DataError, List<String>>
    suspend fun saveFood(): Either<DataError, Unit>
    suspend fun deleteFood(): Either<DataError, Unit>
}
