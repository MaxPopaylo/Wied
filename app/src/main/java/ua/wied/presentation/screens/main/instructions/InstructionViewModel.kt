package ua.wied.presentation.screens.main.instructions

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.base.BaseViewModelWithEvent
import ua.wied.presentation.common.composable.SwipeableItemState
import ua.wied.presentation.screens.main.instructions.model.InstructionsEvent
import ua.wied.presentation.screens.main.instructions.model.InstructionsState
import java.time.LocalDateTime
import javax.inject.Inject

class InstructionViewModel @Inject constructor(
) : BaseViewModelWithEvent<InstructionsState, InstructionsEvent>(InstructionsState()) {


    override fun onEvent(event: InstructionsEvent) {
        when (event) {
            is InstructionsEvent.SearchChanged -> { updateState { it.copy(search = event.value) } }
            is InstructionsEvent.DeletePressed -> {  }
        }
    }

    init {
        viewModelScope.launch {
            val elementsForInstruction = listOf(
                Element(
                    id = 1,
                    title = "Element 1",
                    videoUrl = "http://example.com/video1",
                    instructionId = 1,
                    orderNum = 1
                ),
                Element(
                    id = 2,
                    title = "Element 2",
                    videoUrl = "http://example.com/video2",
                    instructionId = 1,
                    orderNum = 2
                )
            )

            val sampleInstruction1 = Instruction(
                id = 1,
                title = "Instruction 1",
                folderId = 1,
                posterUrl = "https://randomwordgenerator.com/img/picture-generator/52e9d54a4956af14f1dc8460962e33791c3ad6e04e507441722a72dc9e4bc7_640.jpg",
                elements = elementsForInstruction,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                orderNum = 1
            )

            val sampleInstruction2 = Instruction(
                id = 2,
                title = "Instruction 2",
                folderId = 1,
                posterUrl = "https://randomwordgenerator.com/img/picture-generator/52e9d54a4956af14f1dc8460962e33791c3ad6e04e507441722a72dc9e4bc7_640.jpg",
                elements = elementsForInstruction,
                createTime = LocalDateTime.now(),
                updateTime = LocalDateTime.now(),
                orderNum = 2
            )

            val sampleFolder = Folder<SwipeableItemState<Instruction>>(
                id = 1,
                title = "Sample Folder",
                items = listOf(sampleInstruction1, sampleInstruction2).map { SwipeableItemState(item = it) },
                orderNum = 1
            )

            updateState { it.copy(folders = listOf(sampleFolder)) }
        }
    }


}
