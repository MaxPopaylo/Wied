package ua.wied.presentation.screens.main.reports.create.models

import ua.wied.presentation.common.base.BaseState

data class CreateReportState (
    val title: String = "",
    val description: String = "",
    val imgUrls: MutableList<String?> = mutableListOf(),
    override val isLoading: Boolean = false
): BaseState