package ua.wied.presentation.screens.main.reports.status_list

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.usecases.GetReportsByInstructionUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.main.reports.status_list.models.ReportStatusListState
import javax.inject.Inject

@HiltViewModel
class ReportStatusListViewModel @Inject constructor(
    private val getReportsByInstructionUseCase: GetReportsByInstructionUseCase
) : BaseViewModel<ReportStatusListState>(ReportStatusListState()) {

    fun initialize(instructionId: Int) {
        collectNetworkRequest(
            apiCall = { getReportsByInstructionUseCase(instructionId) },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            updateFailure = { updateState { it.copy(isNotInternetConnection = true) } },
            onSuccess = {
                reports -> updateState { it.copy(reports = reports) }
                splitReportsByStatus()
            }
        )
    }

    private fun splitReportsByStatus() {
        if (uiState.value.reports.isNotEmpty()) {
            updateState { state ->
                val todo = state.reports.filter { it.status == ReportStatus.TODO }
                val inProgress = state.reports.filter { it.status == ReportStatus.IN_PROGRESS }
                val done = state.reports.filter { it.status == ReportStatus.DONE }

                state.copy(
                    todoReports = todo,
                    inProgressReports = inProgress,
                    doneReports = done
                )
            }
        }

    }


}