package nl.jaysh.journal.feature.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.jaysh.journal.core.common.Constants.MAX_EMAIL_CHARACTER
import nl.jaysh.journal.core.common.Constants.MAX_PASSWORD_CHARACTER
import nl.jaysh.journal.core.domain.model.DataError
import nl.jaysh.journal.core.domain.usecase.authentication.LoginUseCase
import nl.jaysh.journal.feature.authentication.LoginEvent.OnEmailInputChange
import nl.jaysh.journal.feature.authentication.LoginEvent.OnLogin
import nl.jaysh.journal.feature.authentication.LoginEvent.OnNavigate
import nl.jaysh.journal.feature.authentication.LoginEvent.OnPasswordInputChange
import nl.jaysh.journal.feature.authentication.LoginEvent.OnRegister
import nl.jaysh.journal.feature.authentication.LoginNavigationEvent.NavigateToRegister
import nl.jaysh.journal.feature.authentication.LoginNavigationEvent.NavigateToHome
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEvent(event: LoginEvent) {
        when (event) {
            is OnEmailInputChange -> onEmailInputChange(event.userInput)
            is OnPasswordInputChange -> onPasswordInputChange(event.userInput)
            is OnLogin -> onLogin()
            OnRegister -> onRegister()
            OnNavigate -> onNavigate()
        }
    }

    private fun onEmailInputChange(email: String) {
        if (email.length < MAX_EMAIL_CHARACTER) {
            _state.update { it.copy(email = email) }
        }
    }

    private fun onPasswordInputChange(password: String) {
        if (password.length < MAX_PASSWORD_CHARACTER) {
            _state.update { it.copy(password = password) }
        }
    }

    private fun onLogin() {
        _state.update { it.copy(loading = true) }

        val email = state.value.email
        val password = state.value.password

        val emailValid = validateEmail(email)
        val passwordValid = validatePassword(password)

        if (emailValid && passwordValid) {
            viewModelScope.launch {
                login(email, password).fold(
                    ifLeft = { error -> onLoginFail(error) },
                    ifRight = { onLoginSuccess() },
                )
            }
        }
    }

    private fun onLoginSuccess() {
        val newState = state.value.copy(navigate = NavigateToHome, loading = false, error = false)
        _state.update { newState }
    }

    private fun onLoginFail(error: DataError) {
        val newState = state.value.copy(error = true, loading = false)
        _state.update { newState }
    }

    private fun onRegister() {
        val newState = state.value.copy(navigate = NavigateToRegister)
        _state.update { newState }
    }

    private fun validateEmail(email: String): Boolean {
        val emailToValidate = email.trim()
        val pattern = Regex("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
        val emailValid = emailToValidate.isNotBlank() && emailToValidate.matches(pattern)

        _state.update { it.copy(emailValid = emailValid) }
        return emailValid
    }

    private fun validatePassword(password: String): Boolean {
        val passwordValid = password.isNotBlank()

        _state.update { it.copy(passwordValid = passwordValid) }
        return passwordValid
    }

    private fun onNavigate() {
        _state.update { LoginState() }
    }
}

data class LoginState(
    val loading: Boolean = false,
    val email: String = "",
    val emailValid: Boolean? = null,
    val password: String = "",
    val passwordValid: Boolean? = null,
    val navigate: LoginNavigationEvent? = null,
    val error: Boolean = false,
)

sealed interface LoginEvent {
    data class OnEmailInputChange(val userInput: String) : LoginEvent
    data class OnPasswordInputChange(val userInput: String) : LoginEvent
    data object OnLogin : LoginEvent
    data object OnRegister : LoginEvent
    data object OnNavigate : LoginEvent
}

sealed interface LoginNavigationEvent {
    data object NavigateToRegister : LoginNavigationEvent
    data object NavigateToHome : LoginNavigationEvent
}
