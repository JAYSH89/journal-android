package nl.jaysh.journal.feature.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import nl.jaysh.journal.core.ui.JournalButton
import nl.jaysh.journal.feature.authentication.components.EmailPasswordField

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onRegisterButtonPressed: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.navigate) {
        when (state.navigate) {
            LoginNavigationEvent.NavigateToHome -> {
                onLoginSuccess()
                viewModel.onEvent(LoginEvent.OnNavigate)
            }

            LoginNavigationEvent.NavigateToRegister -> {
                onRegisterButtonPressed()
                viewModel.onEvent(LoginEvent.OnNavigate)
            }

            null -> {}
        }
    }

    LoginScreenContent(state, viewModel::onEvent)
}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    onEvent: (LoginEvent) -> Unit,
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
                text = "Login",
                style = MaterialTheme.typography.displayMedium,
            )
        }

        item {
            EmailPasswordField(
                title = "Welcome",
                email = state.email,
                password = state.password,
                loading = state.loading,
                isErrorEmail = state.emailValid == false,
                isErrorPassword = state.passwordValid == false,
                onEmailChange = { onEvent(LoginEvent.OnEmailInputChange(it)) },
                onPasswordChange = { onEvent(LoginEvent.OnPasswordInputChange(it)) },
            )
        }

        item {
            ScreenActions(loading = state.loading, onEvent = onEvent)
        }
    }
}

@Composable
private fun ScreenActions(
    loading: Boolean,
    modifier: Modifier = Modifier,
    onEvent: (LoginEvent) -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(12.dp),
) {
    JournalButton(
        title = "LOGIN",
        enabled = !loading,
        onClick = { onEvent(LoginEvent.OnLogin) },
    )

    HorizontalDivider()

    Column {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "No account yet? Register here:",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )

        JournalButton(
            title = "REGISTER",
            enabled = !loading,
            onClick = { onEvent(LoginEvent.OnRegister) },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() = MaterialTheme {
    LoginScreenContent(state = LoginState(), onEvent = {})
}
