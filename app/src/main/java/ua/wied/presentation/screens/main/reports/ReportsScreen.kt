package ua.wied.presentation.screens.main.reports

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.screens.main.reports.composable.ReportListItem

@Composable
fun ReportsScreen(
    navController: NavHostController,
    viewModel: ReportViewModel = hiltViewModel()
) {
    val folders by viewModel.folders.collectAsStateWithLifecycle()
    FolderList(
        folders = folders,
        itemView = { instruction ->
            ReportListItem(
                instruction = instruction,
                reportsCount = 0,
                iconOnClick = {}
            )
        }
    )
}