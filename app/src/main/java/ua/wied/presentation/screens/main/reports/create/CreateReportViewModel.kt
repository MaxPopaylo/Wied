package ua.wied.presentation.screens.main.reports.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.presentation.common.base.BaseViewModelWithEvent
import ua.wied.presentation.screens.main.reports.create.models.CreateReportEvent
import ua.wied.presentation.screens.main.reports.create.models.CreateReportState
import javax.inject.Inject

@HiltViewModel
class CreateReportViewModel @Inject constructor(
) : BaseViewModelWithEvent<CreateReportState, CreateReportEvent>(CreateReportState()) {

    override fun onEvent(event: CreateReportEvent) {
        when(event) {
            is CreateReportEvent.TitleChanged -> updateState { it.copy(title = event.value) }
            is CreateReportEvent.DescriptionChanged -> updateState { it.copy(description = event.value) }
            is CreateReportEvent.PhotoAdded -> updateState { state ->
                state.copy(
                    imgUrls = state.imgUrls.toMutableList().also { it.add(event.url) }
                )
            }
            is CreateReportEvent.PhotoDeleted -> updateState { state ->
                state.copy(
                    imgUrls = state.imgUrls.toMutableList().also { it.remove(event.url) }
                )
            }
        }
    }

    fun isFieldsEmpty(): Boolean {
        return uiState.value.title.isNotEmpty() && uiState.value.imgUrls.isNotEmpty()
    }
}