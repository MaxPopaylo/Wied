package ua.wied.presentation.screens.reports.by_status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.report.Report
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.reports.by_status.composable.ReportsByStatusItem

@Composable
fun ReportsByStatusScreen(
    navController: NavHostController,
    instruction: Instruction,
    reports: List<Report>
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = dimen.padding3Xl),
            text = instruction.title,
            style = typography.h4
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
        ) {
            items(
                reports,
                key = { it.id }
            ) { report ->
                ReportsByStatusItem(
                    report = report,
                    onClick = {
                        navController.navigate(ReportNav.ReportDetail(report))
                    }
                )
            }
        }
    }
}