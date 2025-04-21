package ua.wied.presentation.screens.reports

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import ua.wied.domain.usecases.GetInstructionWithReportCountFoldersUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.reports.models.ReportsEvent
import ua.wied.presentation.screens.reports.models.ReportsState
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val getInstructionWithReportCount: GetInstructionWithReportCountFoldersUseCase
) : BaseViewModel<ReportsState, ReportsEvent>(ReportsState()) {

    init {
        initialize()
    }

    override fun onEvent(event: ReportsEvent) {
        when(event) {
            ReportsEvent.Refresh -> { initialize(true) }
        }
    }

    private fun initialize(isRefresh: Boolean = false) {
        collectNetworkRequest(
            apiCall = { getInstructionWithReportCount() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = { updateState { it.copy(isNotInternetConnection = true) } },
            onSuccess = { folderList ->
                updateState {
                    it.copy(
                        folders = folderList,
                        isEmpty = folderList.isEmpty()
                    )
                }
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

}