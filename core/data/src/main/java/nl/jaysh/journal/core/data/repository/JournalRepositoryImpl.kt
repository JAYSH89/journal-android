package nl.jaysh.journal.core.data.repository

import arrow.core.Either
import nl.jaysh.journal.core.domain.model.DataError
import nl.jaysh.journal.core.domain.model.WeightMeasurement
import nl.jaysh.journal.core.domain.repository.JournalRepository
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

    override suspend fun getFood(): Either<DataError, List<String>> {
        service.getFood()
        return Either.Right(emptyList())
    }

    override suspend fun saveFood(): Either<DataError, Unit> {
        service.saveFood()
        return Either.Right(Unit)
    }

    override suspend fun deleteFood(): Either<DataError, Unit> {
        service.deleteFood(id = "")
        return Either.Right(Unit)
    }
}
