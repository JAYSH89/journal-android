package nl.jaysh.journal.feature.food.overview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FoodOverviewViewModel @Inject constructor() : ViewModel() {

    val state: StateFlow<FoodOverviewState> = MutableStateFlow(FoodOverviewState())

    fun onEvent(event: FoodOverviewEvent) {
        when (event) {
            FoodOverviewEvent.OnClick -> TODO()
        }
    }
}

data class FoodOverviewState(
    val loading: Boolean = false,
)

sealed interface FoodOverviewEvent {
    data object OnClick : FoodOverviewEvent
}
