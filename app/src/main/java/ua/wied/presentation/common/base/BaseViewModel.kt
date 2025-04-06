package ua.wied.presentation.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.wied.domain.models.FlowResult

abstract class BaseViewModel<STATE>(initialState: STATE) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    protected fun updateState(block: (STATE) -> STATE) {
        _uiState.update { state -> block(state) }
    }

    protected fun <T : Any> collectNetworkRequest(
        updateLoadingState: (Boolean) -> Unit,
        updateFailure: () -> Unit,
        apiCall: suspend () -> FlowResult<T>,
        onSuccess: (T) -> Unit
    ) {
        viewModelScope.launch {
            updateLoadingState(true)

            apiCall().collect { result ->
                result.fold(
                    onSuccess = {
                        onSuccess(it)
                    },
                    onFailure = {
                        updateFailure()
                    }
                )
            }

            updateLoadingState(false)
        }
    }
}

abstract class BaseViewModelWithEvent<STATE, EVENT>(initialState: STATE) : BaseViewModel<STATE>(initialState) {
    abstract fun onEvent(event: EVENT)
}
