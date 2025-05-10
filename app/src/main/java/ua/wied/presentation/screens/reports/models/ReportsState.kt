package ua.wied.presentation.screens.reports.models

import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.InstructionWithReportCount
import ua.wied.presentation.common.base.BaseNetworkResponseState

data class ReportsState (
    override val isLoading: Boolean = false,
    override val isEmpty: Boolean = false,
    override val isNotInternetConnection: Boolean = false,
    override val isRefreshing: Boolean = false,
    override val requestBeforeNetworkError: () -> Unit = {},
    val folders: List<Folder<InstructionWithReportCount>> = emptyList()
): BaseNetworkResponseState