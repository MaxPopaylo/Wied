package ua.wied.presentation.screens.main.reports.create.models


sealed class CreateReportEvents {
    data class TitleChanged(val value: String): CreateReportEvents()
    data class DescriptionChanged(val value: String): CreateReportEvents()
    data class PhotoAdded(val index: Int, val url: String): CreateReportEvents()
    data class PhotoDeleted(val url: String): CreateReportEvents()
}