package nl.jaysh.journal.feature.food.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.jaysh.journal.core.domain.model.food.AmountType
import nl.jaysh.journal.core.domain.model.food.Food
import nl.jaysh.journal.core.domain.usecase.food.SaveFoodUseCase
import nl.jaysh.journal.feature.food.detail.FoodDetailEvent.OnCarbInputChange
import nl.jaysh.journal.feature.food.detail.FoodDetailEvent.OnFatInputChange
import nl.jaysh.journal.feature.food.detail.FoodDetailEvent.OnNameInputChange
import nl.jaysh.journal.feature.food.detail.FoodDetailEvent.OnProteinInputChange
import nl.jaysh.journal.feature.food.detail.FoodDetailEvent.OnSubmit
import javax.inject.Inject

@HiltViewModel
class FoodDetailViewModel @Inject constructor(
    private val saveFood: SaveFoodUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(FoodDetailState())
    val state: StateFlow<FoodDetailState> = _state

    fun onEvent(event: FoodDetailEvent) {
        when (event) {
            OnSubmit -> {
                submit()
            }

            is OnCarbInputChange -> {
                _state.update { it.copy(carbInput = event.value) }
            }

            is OnFatInputChange -> {
                _state.update { it.copy(fatInput = event.value) }
            }

            is OnNameInputChange -> {
                _state.update { it.copy(nameInput = event.value) }
            }

            is OnProteinInputChange -> {
                _state.update { it.copy(proteinInput = event.value) }
            }

            is FoodDetailEvent.OnAmountChange -> {
                _state.update { it.copy(amountInput = event.value) }
            }

            is FoodDetailEvent.OnAmountTypeInputSelected -> {
                _state.update { it.copy(selectedAmountType = event.value) }
            }

            FoodDetailEvent.OnNavigate -> _state.update { FoodDetailState() }
        }
    }

    private fun submit() {
        _state.update { it.copy(loading = true, error = false) }

        val inputValid = validate()
        if (!inputValid) {
            _state.update { it.copy(loading = false, error = true) }
            return
        }

        viewModelScope.launch {
            val food = Food(
                name = state.value.nameInput.orEmpty(),
                carbs = requireNotNull(state.value.carbInput).toDouble(),
                proteins = requireNotNull(state.value.proteinInput).toDouble(),
                fats = requireNotNull(state.value.fatInput).toDouble(),
                amount = requireNotNull(state.value.amountInput).toDouble(),
                calories = null,
                amountType = requireNotNull(state.value.selectedAmountType),
            )

            saveFood(food).fold(
                ifLeft = {
                    _state.update { it.copy(loading = false, error = true, foodSaved = false) }
                },
                ifRight = {
                    _state.update { it.copy(loading = false, error = false, foodSaved = true) }
                },
            )
        }
    }

    private fun validate(): Boolean {
        state.value.carbInput?.toDoubleOrNull() ?: return false
        state.value.proteinInput?.toDoubleOrNull() ?: return false
        state.value.fatInput?.toDoubleOrNull() ?: return false
        state.value.amountInput?.toDoubleOrNull() ?: return false

        val nameInputValid = !state.value.nameInput.isNullOrBlank()
        val selectedAmountTypeValid = state.value.selectedAmountType != null

        return nameInputValid && selectedAmountTypeValid
    }
}

data class FoodDetailState(
    val loading: Boolean = false,
    val error: Boolean = false,
    val nameInput: String? = null,
    val carbInput: String? = null,
    val proteinInput: String? = null,
    val fatInput: String? = null,
    val amountInput: String? = null,
    val selectedAmountType: AmountType? = null,
    val foodSaved: Boolean = false,
)

sealed interface FoodDetailEvent {
    data object OnSubmit : FoodDetailEvent
    data class OnNameInputChange(val value: String) : FoodDetailEvent
    data class OnCarbInputChange(val value: String) : FoodDetailEvent
    data class OnProteinInputChange(val value: String) : FoodDetailEvent
    data class OnFatInputChange(val value: String) : FoodDetailEvent
    data class OnAmountChange(val value: String) : FoodDetailEvent
    data class OnAmountTypeInputSelected(val value: AmountType) : FoodDetailEvent
    data object OnNavigate : FoodDetailEvent
}
