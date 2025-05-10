package nl.jaysh.journal.feature.food.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor() : ViewModel() {

    val state: StateFlow<FoodDetailState> = MutableStateFlow(FoodDetailState())

    fun onEvent(event: FoodDetailEvent) {
        when (event) {
            FoodDetailEvent.OnClick -> TODO()
        }
    }
}

data class FoodDetailState(
    val loading: Boolean = false,
)

sealed interface FoodDetailEvent {
    data object OnClick : FoodDetailEvent
}
