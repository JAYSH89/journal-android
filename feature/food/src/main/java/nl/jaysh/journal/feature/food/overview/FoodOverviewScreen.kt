package nl.jaysh.journal.feature.food.overview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jaysh.journal.core.ui.R

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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(color = MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun FoodOverviewScreenPreview() = MaterialTheme {
    FoodOverviewScreenContent(
        state = FoodOverviewState(),
        onEvent = {},
        onClickAdd = {},
    )
}
