package nl.jaysh.journal.core.domain.model

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

data class JournalDateTime(val date: LocalDateTime = LocalDateTime.now()) {

    val dateTimeString: String
        get() = format("dd/MM/yyyy HH:mm")

    val dateString: String
        get() = format("dd/MM/yyyy")

    val timeString: String
        get() = format("HH:mm")

    fun parse(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }

    private fun format(format: String): String {
        val formatter = DateTimeFormatter.ofPattern(format)
        return formatter.format(date)
    }

    companion object {
        fun fromMillis(millis: Long): JournalDateTime {
            val instant = Date(millis).toInstant()
            val newDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
            return JournalDateTime(newDate)
        }

        fun from(date: LocalDate, time: LocalTime) = JournalDateTime(LocalDateTime.of(date, time))
    }
}
