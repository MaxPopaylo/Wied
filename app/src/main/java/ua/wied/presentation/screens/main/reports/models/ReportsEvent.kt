package ua.wied.presentation.screens.main.reports.models

sealed class ReportsEvent {
    data object Refresh : ReportsEvent()
}