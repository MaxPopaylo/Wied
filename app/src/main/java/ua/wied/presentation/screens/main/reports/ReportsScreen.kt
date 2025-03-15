package ua.wied.presentation.screens.main.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.screens.main.reports.composable.ReportListItem

@Composable
fun ReportsScreen(
    navController: NavHostController,
    viewModel: ReportViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.primaryBackground)
            .padding(horizontal = 24.dp)
    ) {
        val folders by viewModel.folders.collectAsStateWithLifecycle()
        FolderList(
            folders = folders,
            itemView = { index, instruction ->
                ReportListItem(
                    instruction = instruction,
                    instructionNum = index + 1,
                    reportsCount = 0,
                    iconOnClick = {}
                )
            }
        )
    }
}