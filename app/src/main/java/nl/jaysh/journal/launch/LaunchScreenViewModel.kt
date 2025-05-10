package nl.jaysh.journal.launch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import nl.jaysh.journal.core.domain.usecase.authentication.RefreshUseCase
import nl.jaysh.journal.launch.LaunchScreenState.Loading
import nl.jaysh.journal.launch.LaunchScreenState.NavigateToLogin
import nl.jaysh.journal.launch.LaunchScreenState.NavigateToHome
import javax.inject.Inject

@HiltViewModel
class LaunchScreenViewModel @Inject constructor(private val refresh: RefreshUseCase) : ViewModel() {

    val state: StateFlow<LaunchScreenState> = refreshToken()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
            initialValue = Loading
        )

    private fun refreshToken() = flow {
        refresh().fold(
            ifLeft = { emit(NavigateToLogin) },
            ifRight = { emit(NavigateToHome) }
        )
    }
}

sealed interface LaunchScreenState {
    data object Loading : LaunchScreenState
    data object NavigateToLogin : LaunchScreenState
    data object NavigateToRegister : LaunchScreenState
    data object NavigateToHome : LaunchScreenState
}
