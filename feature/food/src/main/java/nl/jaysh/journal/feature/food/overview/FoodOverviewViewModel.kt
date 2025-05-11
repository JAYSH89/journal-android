package nl.jaysh.journal.feature.food.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.jaysh.journal.core.domain.model.food.Food
import nl.jaysh.journal.core.domain.usecase.food.GetFoodUseCase
import nl.jaysh.journal.feature.food.overview.FoodOverviewEvent.*
import javax.inject.Inject

@HiltViewModel
class FoodOverviewViewModel @Inject constructor(
    private val getFood: GetFoodUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(FoodOverviewState())
    val state: StateFlow<FoodOverviewState> = _state

    init {
        fetchFood()
    }

    fun onEvent(event: FoodOverviewEvent) {
        when (event) {
            is OnSelectFood -> _state.update { it.copy(selectedFood = event.food) }
            is OnSearchInputChanged -> _state.update { it.copy(searchInput = event.input) }
        }
    }

    private fun fetchFood() {
        viewModelScope.launch {
            getFood().fold(
                ifLeft = {
                    _state.update { it.copy(error = true) }
                },
                ifRight = { food ->
                    _state.update { it.copy(error = false, food = food) }
                },
            )
        }
    }
}

data class FoodOverviewState(
    val loading: Boolean = false,
    val searchInput: String = "",
    val selectedFood: Food? = null,
    val food: List<Food> = emptyList(),
    val error: Boolean = false,
)

sealed interface FoodOverviewEvent {
    data class OnSelectFood(val food: Food) : FoodOverviewEvent
    data class OnSearchInputChanged(val input: String) : FoodOverviewEvent
}
