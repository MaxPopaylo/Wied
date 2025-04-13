package ua.wied.presentation.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.wied.domain.models.FlowResult

abstract class BaseViewModel<STATE, EVENT>(initialState: STATE) : ViewModel() {

    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    abstract fun onEvent(event: EVENT)

    protected fun updateState(block: (STATE) -> STATE) {
        _uiState.update { state -> block(state) }
    }

    protected fun <T : Any> collectNetworkRequest(
        updateLoadingState: (Boolean) -> Unit,
        updateFailure: () -> Unit,
        apiCall: suspend () -> FlowResult<T>,
        onRefresh: (suspend (Boolean) -> Unit)? = null,
        onSuccess: suspend (T) -> Unit,
    ) {
        viewModelScope.launch {
            updateLoadingState(true)
            onRefresh?.invoke(true)

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

            onRefresh?.invoke(false)
            updateLoadingState(false)
        }
    }
}