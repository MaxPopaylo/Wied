package ua.wied.presentation.screens.main.reports.create

import dagger.hilt.android.lifecycle.HiltViewModel
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
        collectNetworkRequest(
            apiCall = {
                createReportUseCase(
                    instructionId = instructionId,
                    title = state.title,
                    info = state.description,
                    imageUris = state.imageUris.toList()
                )
            },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = { state.createResult.emit(Result.failure(it)) },
            onSuccess = {
                state.createResult.emit(Result.success(Unit))
            }
        )
    }

    fun isFieldsEmpty(): Boolean {
        return uiState.value.title.isNotEmpty() && uiState.value.imageUris.isNotEmpty()
    }
}