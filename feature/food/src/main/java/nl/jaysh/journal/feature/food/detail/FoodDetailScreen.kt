package nl.jaysh.journal.feature.food.detail

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jaysh.journal.core.domain.model.food.AmountType
import nl.jaysh.journal.core.ui.JournalButton
import nl.jaysh.journal.core.ui.JournalTextField
import nl.jaysh.journal.feature.food.detail.FoodDetailEvent.*

@Composable
fun FoodDetailScreen(
    viewModel: FoodDetailViewModel = hiltViewModel(),
    onSubmit: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.foodSaved) {
        if (state.foodSaved) {
            onSubmit()
            viewModel.onEvent(OnNavigate)
        }
    }

    FoodDetailScreenContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoodDetailScreenContent(state: FoodDetailState, onEvent: (FoodDetailEvent) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Food detail") })
        },
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            FoodInput(state, onEvent)

            JournalButton(
                title = "SUBMIT",
                enabled = !state.loading,
                onClick = { onEvent(OnSubmit) },
            )
        }
    }
}

@Composable
private fun FoodInput(
    state: FoodDetailState,
    onEvent: (FoodDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current

    Column(modifier = modifier.fillMaxWidth()) {
        JournalTextField(
            value = state.nameInput.orEmpty(),
            label = "Name",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
            ),
            onValueChange = { onEvent(OnNameInputChange(it)) },
        )

        JournalTextField(
            value = state.carbInput.orEmpty(),
            label = "Carbs",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
            ),
            onValueChange = { onEvent(OnCarbInputChange(it)) },
        )

        JournalTextField(
            value = state.proteinInput.orEmpty(),
            label = "Proteins",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
            ),
            onValueChange = { onEvent(OnProteinInputChange(it)) },
        )

        JournalTextField(
            value = state.fatInput.orEmpty(),
            label = "Fats",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) },
            ),
            onValueChange = { onEvent(OnFatInputChange(it)) },
        )

        JournalTextField(
            value = state.amountInput.orEmpty(),
            label = "Amount",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Go,
            ),
            keyboardActions = KeyboardActions(onGo = { focusManager.clearFocus() }),
            onValueChange = { onEvent(OnAmountChange(it)) },
        )

        SingleChoiceSegmentedButtonRow {
            AmountType.entries.forEachIndexed { index, amountType ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = AmountType.entries.size,
                    ),
                    onClick = { onEvent(OnAmountTypeInputSelected(amountType)) },
                    selected = state.selectedAmountType == amountType,
                    label = { Text(text = amountType.name) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FoodDetailScreenPreview() = MaterialTheme {
    FoodDetailScreenContent(state = FoodDetailState(), onEvent = {})
}
