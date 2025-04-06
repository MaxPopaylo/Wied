package ua.wied.presentation.screens.main.reports

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.usecases.GetInstructionWithReportCountFoldersUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.main.reports.models.ReportState
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getInstructionWithReportCount: GetInstructionWithReportCountFoldersUseCase
) : BaseViewModel<ReportState>(ReportState()) {

    init {
        initialize()
    }

    private fun initialize() {
        collectNetworkRequest(
            apiCall = { getInstructionWithReportCount() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            updateFailure = { updateState { it.copy(isNotInternetConnection = true) } },
            onSuccess = { folderList -> updateState { it.copy(folders = folderList) } }
        )
    }

}