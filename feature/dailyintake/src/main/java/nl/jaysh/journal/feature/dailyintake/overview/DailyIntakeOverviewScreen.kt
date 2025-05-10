package nl.jaysh.journal.feature.dailyintake.overview

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DailyIntakeOverviewScreen(viewModel: DailyIntakeOverviewViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    DailyIntakeOverviewContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun DailyIntakeOverviewContent(
    state: DailyIntakeOverviewState,
    onEvent: (DailyIntakeOverviewEvent) -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.background),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
) {
    OutlinedButton(
        onClick = { onEvent(DailyIntakeOverviewEvent.OnClick) },
    ) {
        Text(
            text = "Daily Intake",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun DailyIntakeOverviewPreview(modifier: Modifier = Modifier) {
    DailyIntakeOverviewContent(state = DailyIntakeOverviewState(), onEvent = {})
}
