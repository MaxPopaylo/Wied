package ua.wied.presentation.screens.main.instructions

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Element
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.SwipeableItemState
import java.time.LocalDateTime
import javax.inject.Inject

class InstructionViewModel @Inject constructor() : ViewModel() {

    data class InstructionUiState(
        val isLoading: Boolean = false,
        val isEmpty: Boolean = false,
        val isNotInternetConnection: Boolean = false,
        val folders: List<Folder<SwipeableItemState<Instruction>>> = emptyList()
    )

    private var _state = MutableStateFlow(InstructionUiState())
    val state: StateFlow<InstructionUiState> = _state

    init {
        // Create some sample elements for the instructions
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

        // Create sample instructions
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

        // Create a sample folder that contains the instructions
        val sampleFolder = Folder<SwipeableItemState<Instruction>>(
            id = 1,
            title = "Sample Folder",
            items = listOf(sampleInstruction1, sampleInstruction2).map { SwipeableItemState(item = it) },
            orderNum = 1
        )

        // Update the state with the mock folder data
        _state.value = _state.value.copy(
            folders = listOf(sampleFolder)
        )
    }
}
