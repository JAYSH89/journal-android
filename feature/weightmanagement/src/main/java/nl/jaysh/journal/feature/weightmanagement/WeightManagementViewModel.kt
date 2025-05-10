package nl.jaysh.journal.feature.weightmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.jaysh.journal.core.domain.model.DataError
import nl.jaysh.journal.core.domain.model.JournalDateTime
import nl.jaysh.journal.core.domain.model.WeightMeasurement
import nl.jaysh.journal.core.domain.usecase.weightmanagement.DeleteWeightUseCase
import nl.jaysh.journal.core.domain.usecase.weightmanagement.GetWeightUseCase
import nl.jaysh.journal.core.domain.usecase.weightmanagement.SaveWeightUseCase
import nl.jaysh.journal.feature.weightmanagement.WeightManagementEvent.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class WeightManagementViewModel @Inject constructor(
    private val getWeight: GetWeightUseCase,
    private val saveWeight: SaveWeightUseCase,
    private val deleteWeight: DeleteWeightUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(WeightManagementState())
    val state: StateFlow<WeightManagementState> = _state

    init {
        fetchWeight()
    }

    fun onEvent(event: WeightManagementEvent) {
        when (event) {
            OnSaveButtonClicked -> saveWeight()
            is OnClickDelete -> onClickDeleteWeightMeasurement(event.weightMeasurement)
            is OnChangeDate -> _state.update { it.copy(dateInput = event.date) }
            is OnChangeTime -> _state.update { it.copy(timeInput = event.time) }
            is OnChangeWeight -> _state.update { it.copy(weightInput = event.weight) }
        }
    }

    private fun saveWeight() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }

            val date = getDate()
            val time = getTime()
            val weight = state.value.weightInput?.toDoubleOrNull()

            val inputValid = WeightMeasurementValid(
                dateInputValid = date != null,
                timeInputValid = time != null,
                weightInputValid = weight != null,
            )

            if (inputValid.isValid && weight != null) {
                _state.update { it.copy(inputValid = null) }
                saveWeight(weight).fold(
                    ifLeft = ::onSaveWeightFailed,
                    ifRight = { fetchWeight() },
                )
            } else {
                _state.update { it.copy(inputValid = inputValid) }
            }
        }
    }

    private fun fetchWeight() {
        viewModelScope.launch {
            getWeight().fold(
                ifLeft = {
                    val newState = state.value.copy(
                        loading = false,
                        weightMeasurements = emptyList(),
                        error = true,
                    )
                    _state.update { newState }
                },
                ifRight = { weightMeasurements ->
                    val newState = state.value.copy(
                        loading = false,
                        weightMeasurements = weightMeasurements,
                        error = false,
                    )
                    _state.update { newState }
                },
            )
        }
    }

    private fun onClickDeleteWeightMeasurement(weightMeasurement: WeightMeasurement) {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            deleteWeight(weightMeasurement).fold(
                ifLeft = ::onDeleteWeightFailed,
                ifRight = { onDeleteWeightSuccessful(weightMeasurement) },
            )
        }
    }

    private fun onDeleteWeightSuccessful(weightMeasurementToRemove: WeightMeasurement) {
        val measurements = state.value.weightMeasurements
            .filter { measurement -> measurement.id != weightMeasurementToRemove.id }

        _state.update { it.copy(weightMeasurements = measurements, loading = false) }
    }

    private fun onDeleteWeightFailed(error: DataError) {
        _state.update { it.copy(error = true, loading = false) }
    }

    private fun onSaveWeightFailed(error: DataError) {
        _state.update { it.copy(error = true, loading = false) }
    }

    private fun getDate() = try {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        LocalDate.parse(state.value.dateInput.orEmpty(), formatter)
    } catch (e: Exception) {
        null
    }

    private fun getTime() = try {
        LocalTime.parse(state.value.timeInput.orEmpty())
    } catch (e: Exception) {
        null
    }
}

data class WeightManagementState(
    val weightMeasurements: List<WeightMeasurement> = emptyList(),
    val weightInput: String? = null,
    val dateInput: String = JournalDateTime().dateString,
    val timeInput: String = JournalDateTime().timeString,
    val inputValid: WeightMeasurementValid? = null,
    val loading: Boolean = true,
    val error: Boolean = false,
)

sealed interface WeightManagementEvent {
    data class OnClickDelete(val weightMeasurement: WeightMeasurement) : WeightManagementEvent
    data object OnSaveButtonClicked : WeightManagementEvent
    data class OnChangeDate(val date: String) : WeightManagementEvent
    data class OnChangeTime(val time: String) : WeightManagementEvent
    data class OnChangeWeight(val weight: String) : WeightManagementEvent
}

data class WeightMeasurementValid(
    val dateInputValid: Boolean = false,
    val timeInputValid: Boolean = false,
    val weightInputValid: Boolean = false,
) {
    val isValid: Boolean
        get() = dateInputValid && timeInputValid && weightInputValid
}
