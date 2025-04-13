package ua.wied.presentation.screens.main.reports.detail

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.models.user.Role
import ua.wied.domain.usecases.GetUserUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.main.reports.detail.models.ReportDetailEvent
import ua.wied.presentation.screens.main.reports.detail.models.ReportDetailState
import javax.inject.Inject

@HiltViewModel
class ReportDetailViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
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

            val user = getUserUseCase()
            updateState { it.copy(localUser = user, report = report) }

            updateState { it.copy(isLoading = false) }
        }
    }

    private fun changeReportStatus(status: ReportStatus) {
        if (uiState.value.report?.status != status) {
            updateState { it.copy(report = it.report?.copy(status = status)) }
        }
    }

    fun isManager() = uiState.value.localUser?.role?.let { it != Role.EMPLOYEE } ?: false

}