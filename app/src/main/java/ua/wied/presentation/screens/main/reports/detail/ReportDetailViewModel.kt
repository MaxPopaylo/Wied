package ua.wied.presentation.screens.main.reports.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.wied.R
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.models.user.Role
import ua.wied.domain.usecases.GetUserUseCase
import ua.wied.domain.usecases.UpdateReportStatusUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.common.utils.ToastManager
import ua.wied.presentation.screens.main.reports.detail.models.ReportDetailEvent
import ua.wied.presentation.screens.main.reports.detail.models.ReportDetailState
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
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            val user = getUser()
            updateState { it.copy(localUser = user, report = report) }

            updateState { it.copy(isLoading = false) }
        }
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