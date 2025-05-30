package ua.wied.presentation.screens.evaluation.instruction_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import ua.wied.domain.models.evaluation.DateRange
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.pickers.CustomDatePicker
import ua.wied.presentation.common.composable.ItemList
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.evaluation.instruction_list.composable.EvaluationListItem
import ua.wied.presentation.screens.evaluation.instruction_list.composable.InstructionEvaluationsEmptyScreen
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationsEvent
import ua.wied.presentation.screens.evaluation.instruction_list.model.InstructionEvaluationsState
import ua.wied.presentation.screens.main.models.MainEvent

@Composable
fun InstructionEvaluationScreen(
    instruction: Instruction,
    isManager: Boolean,
    savedStateHandle: SavedStateHandle,
    onMainEvent: (MainEvent) -> Unit,
    state: InstructionEvaluationsState,
    onEvent: (InstructionEvaluationsEvent) -> Unit,
    navigateToCreation: (Instruction) -> Unit
) {
    LaunchedEffect(instruction) {
        onEvent(InstructionEvaluationsEvent.LoadData(instruction))
    }

    val shouldRefresh = savedStateHandle
        .getStateFlow("shouldRefresh", false)
        .collectAsState()

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh.value) {
            onEvent(InstructionEvaluationsEvent.Refresh)
            savedStateHandle["shouldRefresh"] = false
        }
    }

    LaunchedEffect(state.instruction) {
        if (isManager && state.instruction != null) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                navigateToCreation(state.instruction)
            }))
        }
    }

    Column(
        modifier = Modifier.padding(bottom = dimen.paddingS),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            SearchField(
                modifier = Modifier.weight(1f),
                text = state.search,
                onSearchValueChange = {
                    onEvent(InstructionEvaluationsEvent.SearchChanged(it))
                }
            )

            CustomDatePicker(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = dimen.paddingXs),
                dateRange = state.dateRange,
                onClearFilter = {
                    onEvent(InstructionEvaluationsEvent.DateRangeChanged(DateRange()))
                },
                onApply = { dateRange ->
                    onEvent(InstructionEvaluationsEvent.DateRangeChanged(dateRange))
                }
            )
        }

        ContentBox(
            state = state,
            onRefresh = { onEvent(InstructionEvaluationsEvent.Refresh) },
            emptyScreen = {
                InstructionEvaluationsEmptyScreen(
                    isFiltered = state.search.isNotEmpty() ||
                                 state.dateRange.startDate == null ||
                                 state.dateRange.startDate == 0L
                )
            }
        ) {
            ItemList (
                items = state.evaluations,
                itemView = { evaluation ->
                    EvaluationListItem(
                        modifier = Modifier.fillMaxWidth(),
                        evaluation = evaluation
                    )
                }
            )
        }
    }

}