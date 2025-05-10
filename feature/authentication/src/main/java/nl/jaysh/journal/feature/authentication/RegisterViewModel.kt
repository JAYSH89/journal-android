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
import nl.jaysh.journal.core.domain.usecase.authentication.RegisterUseCase
import nl.jaysh.journal.feature.authentication.RegisterEvent.OnEmailInputChange
import nl.jaysh.journal.feature.authentication.RegisterEvent.OnNavigate
import nl.jaysh.journal.feature.authentication.RegisterEvent.OnPasswordInputChange
import nl.jaysh.journal.feature.authentication.RegisterEvent.OnRegister
import nl.jaysh.journal.feature.authentication.RegisterNavigationEvent.NavigateToLogin
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val register: RegisterUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<RegisterState> = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state

    fun onEvent(event: RegisterEvent) {
        when (event) {
            is OnEmailInputChange -> onEmailInputChange(event.userInput)
            is OnPasswordInputChange -> onPasswordInputChange(event.userInput)
            is OnRegister -> onRegister()
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

    private fun onRegister() {
        _state.update { it.copy(loading = true) }
        val email = state.value.email
        val password = state.value.password

        val emailValid = validateEmail(email)
        val passwordValid = validatePassword(password)

        if (emailValid && passwordValid) {
            viewModelScope.launch {
                register(email, password).fold(
                    ifLeft = { error -> onRegisterFail(error) },
                    ifRight = { onRegisterSuccess() },
                )
            }
        }
    }

    private fun onRegisterSuccess() {
        val newState = state.value.copy(navigate = NavigateToLogin, loading = false, error = false)
        _state.update { newState }
    }

    private fun onRegisterFail(error: DataError) {
        val newState = state.value.copy(error = true, loading = false)
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
        _state.update { RegisterState() }
    }
}

data class RegisterState(
    val loading: Boolean = false,
    val email: String = "",
    val emailValid: Boolean? = null,
    val password: String = "",
    val passwordValid: Boolean? = null,
    val navigate: RegisterNavigationEvent? = null,
    val error: Boolean = false,
)

sealed interface RegisterEvent {
    data class OnEmailInputChange(val userInput: String) : RegisterEvent
    data class OnPasswordInputChange(val userInput: String) : RegisterEvent
    data object OnRegister : RegisterEvent
    data object OnNavigate : RegisterEvent
}

sealed interface RegisterNavigationEvent {
    data object NavigateToLogin : RegisterNavigationEvent
}
