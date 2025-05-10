package nl.jaysh.journal.core.domain.model

data class WeightMeasurement(
    val id: String,
    val weight: Double,
    val measuredAt: JournalDateTime,
)
