package ua.wied.presentation.screens.reports.models

sealed class ReportsEvent {
    data object Refresh : ReportsEvent()
}