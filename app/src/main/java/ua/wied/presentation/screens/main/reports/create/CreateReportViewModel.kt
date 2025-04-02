package ua.wied.presentation.screens.main.reports.create

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.presentation.screens.main.reports.create.models.CreateReportEvents
import ua.wied.presentation.screens.main.reports.create.models.CreateReportState
import javax.inject.Inject

@HiltViewModel
class CreateReportViewModel @Inject constructor(
) : ViewModel() {

    private var _state by mutableStateOf(CreateReportState())
    val state: CreateReportState get() = _state

    fun onEvent(event: CreateReportEvents) {
        _state = when(event) {
            is CreateReportEvents.TitleChanged -> _state.copy(title = event.value)
            is CreateReportEvents.DescriptionChanged -> _state.copy(description = event.value)
            is CreateReportEvents.PhotoAdded -> _state.copy(
                imgUrls = _state.imgUrls.toMutableList().also { it.add(event.url)}
            )
            is CreateReportEvents.PhotoDeleted -> _state.copy(
                imgUrls = _state.imgUrls.toMutableList().also { it.remove(event.url) }
            )
        }
    }

    fun isFieldsEmpty(): Boolean {
        return _state.title.isNotEmpty() && _state.imgUrls.isNotEmpty()
    }
}