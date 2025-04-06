package ua.wied.presentation.screens.main.reports.create.models


sealed class CreateReportEvent {
    data class TitleChanged(val value: String): CreateReportEvent()
    data class DescriptionChanged(val value: String): CreateReportEvent()
    data class PhotoAdded(val index: Int, val url: String): CreateReportEvent()
    data class PhotoDeleted(val url: String): CreateReportEvent()
}