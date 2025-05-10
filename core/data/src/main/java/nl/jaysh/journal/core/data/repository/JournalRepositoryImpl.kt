package nl.jaysh.journal.core.data.repository

import arrow.core.Either
import nl.jaysh.journal.core.domain.model.DataError
import nl.jaysh.journal.core.domain.model.WeightMeasurement
import nl.jaysh.journal.core.domain.repository.JournalRepository
import nl.jaysh.journal.core.network.model.toWeightMeasurement
import nl.jaysh.journal.core.network.service.JournalService
import javax.inject.Inject

class JournalRepositoryImpl @Inject constructor(
    private val service: JournalService,
) : JournalRepository {

    override suspend fun getWeight() = service.getWeight().map { measurements ->
        measurements.map { it.toWeightMeasurement() }
    }

    override suspend fun saveWeightMeasurement(weight: Double) = service
        .saveWeightMeasurement(weight)
        .map { measurement -> measurement.toWeightMeasurement() }

    override suspend fun deleteWeightMeasurement(
        weightMeasurement: WeightMeasurement,
    ): Either<DataError, Unit> {
        return service.deleteWeight(weightMeasurement.id)
    }
}
