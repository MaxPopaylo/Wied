package ua.wied.presentation.screens.instructions.access

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.wied.R
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.dropdowns.EmployeesBottomSheet
import ua.wied.presentation.common.composable.ItemList
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.hideBottomSheet
import ua.wied.presentation.common.utils.extensions.showBottomSheet
import ua.wied.presentation.screens.accesses.composable.AccessAvailableForEveryone
import ua.wied.presentation.screens.accesses.composable.AccessListItem
import ua.wied.presentation.screens.instructions.access.model.InstructionAccessEvent
import ua.wied.presentation.screens.instructions.access.model.InstructionAccessState
import ua.wied.presentation.screens.main.models.MainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionAccessScreen(
    instructionId: Int,
    isManager: Boolean,
    onMainEvent: (MainEvent) -> Unit,
    state: InstructionAccessState,
    onEvent: (InstructionAccessEvent) -> Unit,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        onEvent(InstructionAccessEvent.LoadData(instructionId))
    }

    LaunchedEffect(isManager) {
        if (isManager) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                showBottomSheet(coroutineScope, bottomSheetState) {
                    onEvent(InstructionAccessEvent.LoadEmployees)
                    showBottomSheet = true
                }
            }))
        }
    }

    ContentBox(
        state = state,
        onRefresh = {
            onEvent(InstructionAccessEvent.Refresh)
        }
    ) {
        Column(
            modifier = Modifier.padding(top = dimen.containerPaddingLarge),
            verticalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
        ) {
            DetailTextField(
                title = stringResource(R.string.instruction_public_url),
                text = state.instructionPublicUrl
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimen.paddingLarge,
                        bottom = dimen.padding2Xl
                    ),
                text = stringResource(R.string.accesses),
                style = typography.h4
            )

            if (state.instruction?.accesses != null && state.instruction.accesses.isNotEmpty()) {
                ItemList (
                    items = state.instruction.accesses,
                    itemView = { access ->
                        AccessListItem (
                            access = access,
                            onDelete = {
                                onEvent(InstructionAccessEvent.AccessToggled(access.id, access.name))
                            }
                        )
                    }
                )
            } else {
                AccessAvailableForEveryone(
                    onAddAccessClick = {
                        showBottomSheet(coroutineScope, bottomSheetState) {
                            onEvent(InstructionAccessEvent.LoadEmployees)
                            showBottomSheet = true
                        }
                    }
                )
            }
        }
    }


    if (showBottomSheet) {
        EmployeesBottomSheet (
            employees = state.employees,
            onClose = {
                hideBottomSheet(coroutineScope, bottomSheetState) {
                    showBottomSheet = false
                }
            },
            userChosen = {
                onEvent(InstructionAccessEvent.AccessToggled(it.id, it.name))
            }
        )
    }
}