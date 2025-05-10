package nl.jaysh.journal.core.network.model

import kotlinx.serialization.Serializable
import nl.jaysh.journal.core.domain.model.JournalDateTime
import nl.jaysh.journal.core.domain.model.WeightMeasurement
import nl.jaysh.journal.core.network.serializer.InstantSerializer
import nl.jaysh.journal.core.network.serializer.LocalDateTimeSerializer
import java.time.Instant
import java.time.LocalDateTime

@Serializable
data class WeightMeasurementResponse(
    val id: String,
    val weight: Double,
    @Serializable(with = LocalDateTimeSerializer::class) val measuredAt: LocalDateTime,
    @Serializable(with = InstantSerializer::class) val createdAt: Instant,
)

fun WeightMeasurementResponse.toWeightMeasurement() = WeightMeasurement(
    id = id,
    weight = weight,
    measuredAt = JournalDateTime(measuredAt),
)
