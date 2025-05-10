package nl.jaysh.journal.feature.weightmanagement

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jaysh.journal.core.domain.model.JournalDateTime
import nl.jaysh.journal.core.domain.model.WeightMeasurement
import nl.jaysh.journal.core.ui.JournalButton
import nl.jaysh.journal.core.ui.JournalTextField
import nl.jaysh.journal.core.ui.R
import nl.jaysh.journal.feature.weightmanagement.WeightManagementEvent.OnChangeDate
import nl.jaysh.journal.feature.weightmanagement.WeightManagementEvent.OnChangeTime
import nl.jaysh.journal.feature.weightmanagement.WeightManagementEvent.OnChangeWeight
import nl.jaysh.journal.feature.weightmanagement.WeightManagementEvent.OnSaveButtonClicked

@Composable
fun WeightManagementScreen(viewModel: WeightManagementViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    WeightManagementScreenContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeightManagementScreenContent(
    state: WeightManagementState,
    onEvent: (WeightManagementEvent) -> Unit,
) {
    var bottomSheetVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Weight management") }) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (!state.loading) {
                        bottomSheetVisible = true
                    }
                },
                content = {
                    Icon(
                        painter = painterResource(R.drawable.ic_add),
                        contentDescription = null,
                    )
                },
            )
        },
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .background(color = MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(state.weightMeasurements) { measurement ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(
                            text = measurement.measuredAt.dateTimeString,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                        Text(
                            text = "${measurement.weight} KG",
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }

                    IconButton(
                        onClick = { onEvent(WeightManagementEvent.OnClickDelete(measurement)) },
                        enabled = !state.loading,
                        content = { Icon(painterResource(R.drawable.ic_delete), null) },
                    )
                }
                HorizontalDivider()
            }
        }

        if (bottomSheetVisible) {
            WeightManagementBottomSheet(
                weight = state.weightInput,
                date = state.dateInput,
                time = state.timeInput,
                onEvent = onEvent,
                onDismissRequest = { bottomSheetVisible = false },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeightManagementBottomSheet(
    weight: String?,
    date: String?,
    time: String?,
    onEvent: (WeightManagementEvent) -> Unit,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier
            .fillMaxSize()
            .imePadding(),
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        contentWindowInsets = { WindowInsets(left = 12.dp, right = 12.dp) },
    ) {
        JournalTextField(
            value = weight.orEmpty(),
            label = "Weight",
            suffix = { Text("KG") },
            icon = R.drawable.ic_scale,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next,
            ),
            onValueChange = { weight -> onEvent(OnChangeWeight(weight)) },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            JournalTextField(
                modifier = Modifier.weight(1f),
                value = date.orEmpty(),
                label = "Date",
                icon = R.drawable.ic_calendar,
                placeholder = { Text(text = "DD/MM/YYYY") },
                onValueChange = { date -> onEvent(OnChangeDate(date)) },
            )

            JournalTextField(
                modifier = Modifier.weight(1f),
                value = time.orEmpty(),
                label = "Time",
                icon = R.drawable.ic_calendar,
                placeholder = { Text(text = "HH:mm") },
                onValueChange = { time -> onEvent(OnChangeTime(time)) },
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        JournalButton(
            title = "SAVE",
            onClick = {
                onEvent(OnSaveButtonClicked)
                onDismissRequest()
            },
        )
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun WeightManagementScreenPreview() = MaterialTheme {
    WeightManagementScreenContent(
        state = WeightManagementState(
            weightMeasurements = listOf(
                WeightMeasurement("", 100.0, JournalDateTime()),
                WeightMeasurement("", 90.0, JournalDateTime()),
                WeightMeasurement("", 80.0, JournalDateTime()),
            )
        ),
        onEvent = {}
    )
}
