package nl.jaysh.journal.feature.dailyintake.overview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DailyIntakeOverviewViewModel @Inject constructor() : ViewModel() {

    val state: StateFlow<DailyIntakeOverviewState> = MutableStateFlow(DailyIntakeOverviewState())

    fun onEvent(event: DailyIntakeOverviewEvent) {
        when (event) {
            DailyIntakeOverviewEvent.OnClick -> TODO()
        }
    }
}

data class DailyIntakeOverviewState(
    val isLoading: Boolean = false
)

sealed interface DailyIntakeOverviewEvent {
    data object OnClick : DailyIntakeOverviewEvent
}
