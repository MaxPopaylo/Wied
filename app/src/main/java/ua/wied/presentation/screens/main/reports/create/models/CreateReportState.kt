package ua.wied.presentation.screens.main.reports.create.models

import kotlinx.coroutines.flow.MutableStateFlow
import ua.wied.presentation.common.base.BaseState

data class CreateReportState (
    val title: String = "",
    val description: String = "",
    val imageUris: MutableList<String?> = mutableListOf(),
    val createResult: MutableStateFlow<Result<Unit>?> = MutableStateFlow(null),
    override val isLoading: Boolean = false
): BaseState