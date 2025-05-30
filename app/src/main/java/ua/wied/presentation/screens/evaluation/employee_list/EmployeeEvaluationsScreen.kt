package ua.wied.presentation.screens.evaluation.employee_list

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
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.pickers.CustomDatePicker
import ua.wied.presentation.common.composable.ItemList
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.evaluation.employee_list.model.EmployeeEvaluationsEvent
import ua.wied.presentation.screens.evaluation.employee_list.model.EmployeeEvaluationsState
import ua.wied.presentation.screens.evaluation.instruction_list.composable.EvaluationListItem
import ua.wied.presentation.screens.evaluation.instruction_list.composable.InstructionEvaluationsEmptyScreen
import ua.wied.presentation.screens.main.models.MainEvent

@Composable
fun EmployeeEvaluationScreen(
    employee: User,
    state: EmployeeEvaluationsState,
    isManager: Boolean,
    savedStateHandle: SavedStateHandle,
    onMainEvent: (MainEvent) -> Unit,
    onEvent: (EmployeeEvaluationsEvent) -> Unit,
    navigateToCreation: (User) -> Unit
) {
    LaunchedEffect(employee) {
        onEvent(EmployeeEvaluationsEvent.LoadData(employee))
    }

    val shouldRefresh = savedStateHandle
        .getStateFlow("shouldRefresh", false)
        .collectAsState()

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh.value) {
            onEvent(EmployeeEvaluationsEvent.Refresh)
            savedStateHandle["shouldRefresh"] = false
        }
    }

    LaunchedEffect(state.employee) {
        if (isManager && state.employee != null) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                navigateToCreation(state.employee)
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
                    onEvent(EmployeeEvaluationsEvent.SearchChanged(it))
                }
            )

            CustomDatePicker(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(horizontal = dimen.paddingXs),
                dateRange = state.dateRange,
                onClearFilter = {
                    onEvent(EmployeeEvaluationsEvent.DateRangeChanged(DateRange()))
                },
                onApply = { dateRange ->
                    onEvent(EmployeeEvaluationsEvent.DateRangeChanged(dateRange))
                }
            )
        }

        ContentBox(
            state = state,
            onRefresh = { onEvent(EmployeeEvaluationsEvent.Refresh) },
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
                        isEmployeeEvaluation = true,
                        evaluation = evaluation
                    )
                }
            )
        }
    }

}