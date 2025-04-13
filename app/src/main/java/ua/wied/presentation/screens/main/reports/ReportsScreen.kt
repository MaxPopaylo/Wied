package ua.wied.presentation.screens.main.reports

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.screens.main.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.main.reports.composable.ReportListItem
import ua.wied.presentation.screens.main.reports.models.ReportsEvent

@Composable
fun ReportsScreen(
    navController: NavHostController,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ContentBox(
        state = state,
        onRefresh = { viewModel.onEvent(ReportsEvent.Refresh) },
        emptyScreen = { InstructionEmptyScreen() }
    ) {
        FolderList(
            folders = state.folders,
            itemView = { data ->
                ReportListItem(
                    instruction = data.instruction,
                    reportsCount = data.reportCount,
                    reportsIconOnClick = {
                        navController.navigate(ReportNav.ReportStatusList(data.instruction))
                    },
                    createIconOnClick = {
                        navController.navigate(ReportNav.CreateReport(data.instruction))
                    }
                )
            }
        )
    }

}