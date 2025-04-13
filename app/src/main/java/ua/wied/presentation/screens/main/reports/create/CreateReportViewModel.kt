package ua.wied.presentation.screens.main.reports.create

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ua.wied.domain.usecases.CreateReportUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.main.reports.create.models.CreateReportEvent
import ua.wied.presentation.screens.main.reports.create.models.CreateReportState
import javax.inject.Inject

@HiltViewModel
class CreateReportViewModel @Inject constructor(
    private val createReportUseCase: CreateReportUseCase
) : BaseViewModel<CreateReportState, CreateReportEvent>(CreateReportState()) {

    override fun onEvent(event: CreateReportEvent) {
        when(event) {
            is CreateReportEvent.TitleChanged -> updateState { it.copy(title = event.value) }
            is CreateReportEvent.DescriptionChanged -> updateState { it.copy(description = event.value) }
            is CreateReportEvent.PhotoAdded -> updateState { state ->
                state.copy(
                    imageUris = state.imageUris.toMutableList().also { it.add(event.url) }
                )
            }
            is CreateReportEvent.PhotoDeleted -> updateState { state ->
                state.copy(
                    imageUris = state.imageUris.toMutableList().also { it.remove(event.url) }
                )
            }
            is CreateReportEvent.Create -> {
                createReport(event.instructionId)
            }
        }
    }

    private fun createReport(instructionId: Int) {
        val state = uiState.value
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            val response = createReportUseCase(
                instructionId = instructionId,
                title = state.title,
                info = state.description,
                imageUris = state.imageUris.toList()
            )
            state.createResult.emit(response.first())
            updateState { it.copy(isLoading = false) }
        }
    }

    fun isFieldsEmpty(): Boolean {
        return uiState.value.title.isNotEmpty() && uiState.value.imageUris.isNotEmpty()
    }
}