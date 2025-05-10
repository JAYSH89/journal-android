package nl.jaysh.journal.launch

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jaysh.journal.core.ui.FullScreenLoader

@Composable
fun LaunchScreen(
    viewModel: LaunchScreenViewModel = hiltViewModel(),
    navigateToRegister: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        when (state) {
            LaunchScreenState.NavigateToRegister -> navigateToRegister()
            LaunchScreenState.NavigateToLogin -> navigateToLogin()
            LaunchScreenState.NavigateToHome -> navigateToHome()
            LaunchScreenState.Loading -> {}
        }
    }

    LaunchScreenContent(state = state)
}

@Composable
private fun LaunchScreenContent(state: LaunchScreenState) {
    FullScreenLoader(message = "Loading...")
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun LaunchScreenPreview() = MaterialTheme {
    LaunchScreenContent(state = LaunchScreenState.Loading)
}
