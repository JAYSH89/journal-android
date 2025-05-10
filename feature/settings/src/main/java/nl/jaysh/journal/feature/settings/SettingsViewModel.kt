package nl.jaysh.journal.feature.settings

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : ViewModel() {

    val state: StateFlow<SettingsState> = MutableStateFlow(SettingsState())

    fun onEvent(event: SettingsEvent) {
        when (event) {
            SettingsEvent.OnClick -> TODO()
        }
    }
}

data class SettingsState(
    val isLoading: Boolean = false,
)

sealed interface SettingsEvent {
    data object OnClick : SettingsEvent
}