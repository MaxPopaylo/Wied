package ua.wied.presentation.screens.evaluation.create

import dagger.hilt.android.lifecycle.HiltViewModel
import ua.wied.domain.models.evaluation.ItemEvaluation
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.usecases.CreateEvaluationUseCase
import ua.wied.domain.usecases.GetEmployeesUseCase
import ua.wied.domain.usecases.GetInstructionFoldersUseCase
import ua.wied.presentation.common.base.BaseViewModel
import ua.wied.presentation.common.utils.extensions.toEvaluation
import ua.wied.presentation.screens.evaluation.create.model.CreateEvaluationEvent
import ua.wied.presentation.screens.evaluation.create.model.CreateEvaluationState
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CreateEvaluationViewModel @Inject constructor(
    private val createEvaluationUseCase: CreateEvaluationUseCase,
    private val getEmployeesUseCase: GetEmployeesUseCase,
    private val getInstructionFoldersUseCase: GetInstructionFoldersUseCase,
): BaseViewModel<CreateEvaluationState, CreateEvaluationEvent>(CreateEvaluationState()) {

    override fun onEvent(event: CreateEvaluationEvent) {
        when(event) {
            is CreateEvaluationEvent.OnInstructionChanged -> updateInstruction(event.value)
            is CreateEvaluationEvent.OnEmployeeChanged -> updateState { it.copy(employee = event.value) }
            is CreateEvaluationEvent.OnDateChanged -> updateState { it.copy(createdAt = event.value) }
            is CreateEvaluationEvent.OnItemEvaluationChanged -> updateItemEvaluation(event.elementId, event.evaluation)
            is CreateEvaluationEvent.OnInfoChanged -> updateState { it.copy(info = event.value) }
            is CreateEvaluationEvent.LoadEmployees -> getEmployees()
            is CreateEvaluationEvent.LoadInstructions -> getInstructions()
            is CreateEvaluationEvent.Create -> createEvaluation()
        }
    }

    private fun updateInstruction(instruction: Instruction) {
        val itemEvaluations = instruction.elements.map { element ->
            ItemEvaluation(
                elementId = element.id,
                title = element.title,
                evaluation = 0.0
            )
        }
        updateState { state ->
            state.copy(
                instruction = instruction,
                finalEvaluation = calculateAverage(itemEvaluations),
                itemsEvaluation = itemEvaluations
            )
        }
    }

    private fun updateItemEvaluation(elementId: Int, newRating: Float) {
        updateState { state ->
            val updatedItems = state.itemsEvaluation.map { item ->
                if (item.elementId == elementId)item.copy(evaluation = newRating.toEvaluation()) else item
            }
            state.copy(
                itemsEvaluation = updatedItems,
                finalEvaluation = calculateAverage(updatedItems)
            )
        }
    }

    private val calculateAverage: (List<ItemEvaluation>) -> Double = { items ->
        if (items.isEmpty()) 0.0 else ((items.map { it.evaluation }.average() * 100).roundToInt() / 100.0)
    }

    private fun createEvaluation() {
        val state = uiState.value

        if (state.instruction != null && state.employee != null) {
            collectNetworkRequest(
                apiCall = {
                    createEvaluationUseCase(
                        instructionId = state.instruction.id,
                        employeeId = state.employee.id,
                        itemsEvaluation = state.itemsEvaluation,
                        info = state.info,
                        createTime = state.createdAt,
                    )
                },
                updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
                onFailure = { state.createResult.emit(Result.failure(it)) },
                onSuccess = {
                    state.createResult.emit(Result.success(Unit))
                }
            )
        }
    }

    private fun getEmployees() {
        collectNetworkRequest(
            apiCall = { getEmployeesUseCase() },
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { employees ->
                val updatedEmployees = employees.filter { emp -> emp != uiState.value.employee }

                updateState {
                    it.copy(
                        employees = updatedEmployees,
                        isEmpty = employees.isEmpty(),
                        isNotInternetConnection = false
                    )
                }
            }
        )
    }

    private fun getInstructions() {
        collectNetworkRequest(
            apiCall = { getInstructionFoldersUseCase()},
            updateLoadingState = { value -> updateState { it.copy(isLoading = value) } },
            onFailure = {
                updateState { it.copy(isNotInternetConnection = true) }
            },
            onSuccess = { folders ->
                val updatedInstructions = folders
                    .flatMap { folder -> folder.items }

                updateState {
                    it.copy(
                        instructions = updatedInstructions,
                        isEmpty = updatedInstructions.isEmpty(),
                        isNotInternetConnection = false
                    )
                }
            }
        )
    }
}