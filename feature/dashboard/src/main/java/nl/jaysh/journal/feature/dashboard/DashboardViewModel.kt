package nl.jaysh.journal.feature.dashboard

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor() : ViewModel() {
    
    val state: StateFlow<DashboardState> = MutableStateFlow(DashboardState())

    fun onEvent(event: DashboardEvent) {
        when (event) {
            DashboardEvent.OnClick -> TODO()
        }
    }
}

data class DashboardState(
    val isLoading: Boolean = false,
)

sealed interface DashboardEvent {
    data object OnClick : DashboardEvent
}
