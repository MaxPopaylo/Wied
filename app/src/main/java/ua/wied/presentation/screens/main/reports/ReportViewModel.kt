package ua.wied.presentation.screens.main.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.domain.usecases.GetInstructionWithReportCountFoldersUseCase
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getInstructionWithReportCount: GetInstructionWithReportCountFoldersUseCase
) : ViewModel() {

    data class ReportUiState (
        val isLoading: Boolean = false,
        val isEmpty: Boolean = false,
        val isNotInternetConnection: Boolean = false,
        val folders: List<Folder<InstructionWithReportCount>> = emptyList()
    )

    private var _state = MutableStateFlow(ReportUiState())
    val state: StateFlow<ReportUiState> = _state

    init {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true
            )
            getInstructionWithReportCount().collect { result ->
                result.fold(
                    onSuccess = { folderList ->
                        _state.value = _state.value.copy(
                            folders = folderList
                        )
                    },
                    onFailure = { exception ->
                        _state.value = _state.value.copy(
                            isNotInternetConnection = true
                        )
                    }
                )
            }
            _state.value = _state.value.copy(
                isLoading = false
            )
        }
    }

}