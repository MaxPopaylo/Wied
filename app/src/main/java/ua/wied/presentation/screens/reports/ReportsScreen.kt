package ua.wied.presentation.screens.reports

import androidx.compose.runtime.Composable
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.screens.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.reports.composable.ReportListItem
import ua.wied.presentation.screens.reports.models.ReportsEvent
import ua.wied.presentation.screens.reports.models.ReportsState

@Composable
fun ReportsScreen(
    state: ReportsState,
    onEvent: (ReportsEvent) -> Unit,
    navigateToStatusList: (Instruction) -> Unit,
    navigateToCreateReport: (Instruction) -> Unit
) {
    ContentBox(
        state = state,
        onRefresh = { onEvent(ReportsEvent.Refresh) },
        emptyScreen = { InstructionEmptyScreen() }
    ) {
        FolderList(
            folders = state.folders,
            itemView = { data ->
                ReportListItem(
                    instruction = data.instruction,
                    reportsCount = data.reportCount,
                    reportsIconOnClick = { navigateToStatusList(data.instruction) },
                    createIconOnClick = { navigateToCreateReport(data.instruction) }
                )
            }
        )
    }

}