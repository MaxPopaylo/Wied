package ua.wied.presentation.screens.reports.status_list.models

sealed class ReportStatusListEvent {
    data class LoadData(val instructionId: Int): ReportStatusListEvent()
    data class Refresh(val instructionId: Int) : ReportStatusListEvent()
}