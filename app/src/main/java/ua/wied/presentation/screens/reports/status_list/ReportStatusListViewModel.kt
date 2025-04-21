package ua.wied.presentation.screens.reports.status_list

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.usecases.GetReportsByInstructionUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.reports.status_list.models.ReportStatusListEvent
import ua.wied.presentation.screens.reports.status_list.models.ReportStatusListState
import javax.inject.Inject

@HiltViewModel
class ReportStatusListViewModel @Inject constructor(
    private val getReportsByInstructionUseCase: GetReportsByInstructionUseCase
) : BaseViewModel<ReportStatusListState, ReportStatusListEvent>(ReportStatusListState()) {


    override fun onEvent(event: ReportStatusListEvent) {
        when(event) {
            is ReportStatusListEvent.LoadData -> { initialize(event.instructionId) }
            is ReportStatusListEvent.Refresh -> { initialize(event.instructionId, true) }
        }
    }

    private fun initialize(instructionId: Int, isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getReportsByInstructionUseCase(instructionId) },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = { updateState { it.copy(isNotInternetConnection = true) } },
            onSuccess = { reports ->
                updateState { it.copy(reports = reports)}
                splitReportsByStatus()
                updateState { it.copy(isNotInternetConnection = false) }
            },
            onRefresh = { value ->
                if (isRefresh) {
                    if (!value) delay(100)
                    updateState { it.copy(isRefreshing = value ) }
                }
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