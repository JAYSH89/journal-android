package nl.jaysh.journal.feature.food.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.jaysh.journal.core.domain.model.food.Food
import nl.jaysh.journal.core.domain.usecase.food.DeleteFoodUseCase
import nl.jaysh.journal.core.domain.usecase.food.GetFoodUseCase
import nl.jaysh.journal.feature.food.overview.FoodOverviewEvent.*
import javax.inject.Inject

@HiltViewModel
class FoodOverviewViewModel @Inject constructor(
    private val getFood: GetFoodUseCase,
    private val deleteFood: DeleteFoodUseCase,
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
            is OnDeleteFood -> delete(event.id)
        }
    }

    private fun fetchFood() {
        _state.update { it.copy(loading = true, error = false) }

        viewModelScope.launch {
            getFood().fold(
                ifLeft = {
                    _state.update { it.copy(loading = false, error = true) }
                },
                ifRight = { food ->
                    _state.update { it.copy(loading = false, error = false, food = food) }
                },
            )
        }
    }

    private fun delete(id: String) {
        _state.update { it.copy(loading = true, error = false) }

        viewModelScope.launch {
            deleteFood(id).fold(
                ifLeft = { _state.update { it.copy(loading = false, error = true) } },
                ifRight = {
                    _state.update { it.copy(loading = false, error = false) }
                    fetchFood()
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
    data class OnDeleteFood(val id: String) : FoodOverviewEvent
}
