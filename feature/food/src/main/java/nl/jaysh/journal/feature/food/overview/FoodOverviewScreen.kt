package nl.jaysh.journal.feature.food.overview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jaysh.journal.core.domain.model.food.AmountType
import nl.jaysh.journal.core.domain.model.food.Food
import nl.jaysh.journal.core.ui.JournalTextField
import nl.jaysh.journal.core.ui.MacroTile
import nl.jaysh.journal.core.ui.R
import nl.jaysh.journal.feature.food.overview.FoodOverviewEvent.OnSearchInputChanged

@Composable
fun FoodOverviewScreen(
    viewModel: FoodOverviewViewModel = hiltViewModel(),
    onClickAdd: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FoodOverviewScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
        onClickAdd = onClickAdd,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FoodOverviewScreenContent(
    state: FoodOverviewState,
    onEvent: (FoodOverviewEvent) -> Unit,
    onClickAdd: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Food overview") },
                actions = {
                    IconButton(onClick = onClickAdd) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = null
                        )
                    }
                }
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .padding(contentPadding)
                .background(color = MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            item {
                JournalTextField(
                    value = state.searchInput,
                    label = "Search",
                    onValueChange = { onEvent(OnSearchInputChanged(it)) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.ic_search),
                            contentDescription = null
                        )
                    }
                )
            }
            items(state.food) { food ->
                // TODO: 💩💩💩
                when {
                    state.searchInput.isNotBlank() -> {
                        if (food.name.contains(state.searchInput)) FoodItem(food)
                    }
                    else -> FoodItem(food)
                }
            }
        }
    }
}

@Composable
private fun FoodItem(
    food: Food,
    modifier: Modifier = Modifier,
) = Column(
    modifier = modifier.padding(12.dp),
    verticalArrangement = Arrangement.spacedBy(8.dp),
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = food.name,
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = "(${food.amount} ${food.amountType})",
            style = MaterialTheme.typography.bodyMedium,
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MacroTile(
            modifier = Modifier.weight(1f),
            emoji = "\uD83D\uDD25",
            name = "Calories",
            value = "${food.calories}g",
        )
        MacroTile(
            modifier = Modifier.weight(1f),
            emoji = "\uD83C\uDF5E",
            name = "Carbs",
            value = "${food.carbs}g",
        )
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        MacroTile(
            modifier = Modifier.weight(1f),
            emoji = "\uD83C\uDF57",
            name = "Proteins",
            value = "${food.proteins}g",
        )
        MacroTile(
            modifier = Modifier.weight(1f),
            emoji = "\uD83E\uDD51",
            name = "Fats",
            value = "${food.fats}g",
        )
    }
    HorizontalDivider()
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FoodOverviewScreenPreview() = MaterialTheme {
    FoodOverviewScreenContent(
        state = FoodOverviewState(
            food = listOf(
                Food(
                    name = "Egg",
                    carbs = 1.0,
                    proteins = 2.0,
                    fats = 3.0,
                    amount = 1.0,
                    calories = 100.0,
                    amountType = AmountType.PIECE,
                ),
            ),
        ),
        onEvent = {},
        onClickAdd = {},
    )
}
