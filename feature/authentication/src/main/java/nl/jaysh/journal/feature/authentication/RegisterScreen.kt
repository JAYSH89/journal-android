package nl.jaysh.journal.feature.authentication

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jaysh.journal.core.ui.JournalButton
import nl.jaysh.journal.feature.authentication.RegisterEvent.OnNavigate
import nl.jaysh.journal.feature.authentication.RegisterNavigationEvent.NavigateToLogin
import nl.jaysh.journal.feature.authentication.components.EmailPasswordField

@Composable
fun RegisterScreen(viewModel: RegisterViewModel = hiltViewModel(), onRegisterSuccess: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigate) {
        when (state.navigate) {
            NavigateToLogin -> {
                onRegisterSuccess()
                viewModel.onEvent(OnNavigate)
            }

            null -> {}
        }
    }

    RegisterScreenContent(state = state, onEvent = viewModel::onEvent)
}

@Composable
private fun RegisterScreenContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
) = Scaffold(modifier = Modifier.fillMaxSize()) { contentPadding ->
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding)
            .padding(16.dp)
            .imePadding()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        item {
            Text(
                text = "Register",
                style = MaterialTheme.typography.displayMedium,
            )
        }

        item {
            EmailPasswordField(
                title = "Create account",
                email = state.email,
                password = state.password,
                loading = state.loading,
                onEmailChange = { onEvent(RegisterEvent.OnEmailInputChange(it)) },
                onPasswordChange = { onEvent(RegisterEvent.OnPasswordInputChange(it)) },
            )
        }

        item {
            JournalButton(
                title = "REGISTER",
                enabled = !state.loading,
                onClick = { onEvent(RegisterEvent.OnRegister) },
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun RegisterScreenPreview() = MaterialTheme {
    RegisterScreenContent(
        state = RegisterState(),
        onEvent = {},
    )
}
