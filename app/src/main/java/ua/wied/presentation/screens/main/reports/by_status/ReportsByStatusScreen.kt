package ua.wied.presentation.screens.main.reports.by_status

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.report.Report
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.main.reports.by_status.composable.ReportsByStatusItem

@Composable
fun ReportsByStatusScreen(
    navController: NavHostController,
    instruction: Instruction,
    reports: List<Report>
) {
    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.10f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = instruction.title,
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 26.sp
                )
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                reports,
                key = { it.id }
            ) { report ->
                ReportsByStatusItem(
                    report = report,
                    onClick = {}
                )
            }
        }
    }
}