package ua.wied.presentation.screens.main.reports.by_status

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ua.wied.domain.models.report.Report
import ua.wied.presentation.screens.main.reports.status_list.ReportStatusListViewModel

@Composable
fun ReportsByStatusScreen(
    navController: NavHostController,
    reports: List<Report>,
    viewModel: ReportStatusListViewModel = hiltViewModel()
) {
}