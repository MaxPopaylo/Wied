package ua.wied.presentation.screens.main.reports.create.models

data class CreateReportState (
    val title: String = "",
    val description: String = "",
    val imgUrls: MutableList<String?> = mutableListOf()
)