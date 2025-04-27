package ua.wied.presentation.screens.instructions.elements.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementEvent
import ua.wied.presentation.screens.instructions.elements.create.model.CreateElementState
import javax.inject.Inject

@HiltViewModel
class CreateElementViewModel @Inject constructor(
) : BaseViewModel<CreateElementState, CreateElementEvent>(CreateElementState()) {

    override fun onEvent(event: CreateElementEvent) {
        when(event) {
            is CreateElementEvent.OnTitleChanged -> updateState { it.copy(title = event.value) }
            is CreateElementEvent.OnInfoChanged -> updateState { it.copy(info = event.value) }
            is CreateElementEvent.OnVideoUrlChanged -> updateState { it.copy(videoUrl = event.value) }
            is CreateElementEvent.Create -> { createElement(event.orderNum) }
        }
    }

    private fun createElement(orderNum: Int) {

    }
}