package ua.wied.presentation.screens.reports.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.R
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.models.user.Role
import ua.wied.domain.usecases.GetUserUseCase
import ua.wied.domain.usecases.UpdateReportStatusUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.common.utils.ToastManager
import ua.wied.presentation.screens.reports.detail.models.ReportDetailEvent
import ua.wied.presentation.screens.reports.detail.models.ReportDetailState
import javax.inject.Inject

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    private val getUser: GetUserUseCase,
    private val updateStatus: UpdateReportStatusUseCase
) : BaseViewModel<ReportDetailState, ReportDetailEvent>(ReportDetailState()) {


    override fun onEvent(event: ReportDetailEvent) {
        when(event) {
            is ReportDetailEvent.LoadData -> { initialize(event.report) }
            is ReportDetailEvent.ChangeStatus -> { changeReportStatus(event.status) }
        }
    }

    private fun initialize(report: Report) {
        collectLocalRequest(
            call = { getUser() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            callback = { result ->
                updateState { it.copy(localUser = result, report = report) }
            }
        )
    }

    private fun changeReportStatus(status: ReportStatus) {
        val state = uiState.value
        if (state.report != null && state.report.status != status) {
            collectNetworkRequest(
                apiCall = { updateStatus(state.report.id, status) },
                updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
                onFailure = {
                    ToastManager.showToast(R.string.error)
                },
                onSuccess = {
                    updateState { it.copy(report = state.report.copy(status = status)) }
                }
            )
        }
    }


    fun isManager() = uiState.value.localUser?.role?.let { it != Role.EMPLOYEE }

}