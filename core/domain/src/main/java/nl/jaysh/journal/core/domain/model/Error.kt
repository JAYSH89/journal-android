package nl.jaysh.journal.core.domain.model

interface Error

sealed interface DataError : Error {
    enum class Network : DataError {
        UNKNOWN
    }
}
