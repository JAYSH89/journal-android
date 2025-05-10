package nl.jaysh.journal.feature.dailyintake.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DailyIntakeDetailViewModel @Inject constructor() : ViewModel() {

    val state: StateFlow<DailyIntakeDetailState> = MutableStateFlow(DailyIntakeDetailState())

    fun onEvent(event: DailyIntakeDetailEvent) {
        when (event) {
            DailyIntakeDetailEvent.OnClick -> TODO()
        }
    }
}

data class DailyIntakeDetailState(
    val isLoading: Boolean = false
)

sealed interface DailyIntakeDetailEvent {
    data object OnClick : DailyIntakeDetailEvent
}
