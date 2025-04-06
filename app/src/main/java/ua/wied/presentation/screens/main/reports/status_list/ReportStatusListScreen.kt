package ua.wied.presentation.screens.main.reports.status_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.report.ReportStatus
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick

@Composable
fun ReportStatusListScreen(
    navController: NavHostController,
    instruction: Instruction,
    viewModel: ReportStatusListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.20f),
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

        ReportStatusItem(
            modifier = Modifier,
            title = stringResource(R.string.new_reports),
            reportsCount = state.todoReports.size,
            itemClick = {
                navController.navigate(ReportNav.ReportsByStatusList(
                    reports = state.inProgressReports,
                    instruction = instruction,
                    status = ReportStatus.TODO.name
                ))
            }
        )

        ReportStatusItem(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.in_progress_reports),
            reportsCount = state.inProgressReports.size,
            itemClick = {
                navController.navigate(ReportNav.ReportsByStatusList(
                    reports = state.inProgressReports,
                    instruction = instruction,
                    status = ReportStatus.IN_PROGRESS.name
                ))
            }
        )

        ReportStatusItem(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.done_reports),
            reportsCount = state.doneReports.size,
            itemClick = {
                navController.navigate(ReportNav.ReportsByStatusList(
                    reports = state.doneReports,
                    instruction = instruction,
                    status = ReportStatus.DONE.name
                ))
            }
        )
    }
}

@Composable
private fun ReportStatusItem(
    modifier: Modifier = Modifier,
    title: String,
    reportsCount: Int,
    itemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .bounceClick(itemClick)
            .background(
                color = colors.secondaryBackground,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 10.dp)
            .padding(start = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = colors.primaryText,
            style = typography.w500.copy(
                fontSize = 20.sp
            )
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .height(30.dp)
                .widthIn(30.dp)
                .border(
                    1.25.dp,
                    colors.primaryText,
                    RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = "$reportsCount",
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.width(4.dp))

        Icon(
            modifier = Modifier.rotate(180f).size(25.dp),
            painter = painterResource(R.drawable.icon_arrow_back),
            tint = colors.tintColor,
            contentDescription = stringResource(R.string.icon)
        )
    }
}