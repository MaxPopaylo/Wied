package ua.wied.presentation.screens.reports.status_list

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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick
import ua.wied.presentation.screens.reports.status_list.models.ReportStatusListEvent
import ua.wied.presentation.screens.reports.status_list.models.ReportStatusListState

@Composable
fun ReportStatusListScreen(
    instruction: Instruction,
    state: ReportStatusListState,
    onEvent: (ReportStatusListEvent) -> Unit,
    navigateToReportsByStatus: (List<Report>, Instruction, String) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(ReportStatusListEvent.LoadData(instruction.id))
    }

    val todoReportsCount = state.todoReports.size
    val inProgressReportsCount = state.inProgressReports.size
    val doneReportsCount = state.doneReports.size

    ContentBox(
        state = state,
        onRefresh = {}
    ) {
        Column {
            Spacer(Modifier.fillMaxHeight(.15f))
            Text(
                modifier = Modifier.padding(vertical = dimen.padding3Xl),
                text = instruction.title,
                style = typography.h4
            )

            ReportStatusItem(
                modifier = Modifier,
                title = stringResource(R.string.new_reports),
                reportsCount = todoReportsCount,
                itemClick = {
                    if (todoReportsCount > 0) {
                        navigateToReportsByStatus(
                            state.todoReports,
                            instruction,
                            ReportStatus.TODO.name
                        )
                    }
                }
            )

            ReportStatusItem(
                modifier = Modifier.padding(top = dimen.paddingLarge),
                title = stringResource(R.string.in_progress_reports),
                reportsCount = inProgressReportsCount,
                itemClick = {
                    if (inProgressReportsCount > 0) {
                        navigateToReportsByStatus(
                            state.inProgressReports,
                            instruction,
                            ReportStatus.IN_PROGRESS.name
                        )
                    }
                }
            )

            ReportStatusItem(
                modifier = Modifier.padding(top = dimen.paddingLarge),
                title = stringResource(R.string.done_reports),
                reportsCount = doneReportsCount,
                itemClick = {
                    if (doneReportsCount > 0) {
                        navigateToReportsByStatus(
                            state.doneReports,
                            instruction,
                            ReportStatus.DONE.name
                        )
                    }
                }
            )
        }
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
            .clip(dimen.shape)
            .bounceClick(itemClick)
            .background(
                color = colors.secondaryBackground,
                shape = dimen.shape
            )
            .padding(vertical = dimen.paddingXl)
            .padding(start = dimen.paddingXl, end = dimen.paddingXs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = typography.h4
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .height(30.dp)
                .widthIn(30.dp)
                .border(
                    1.25.dp,
                    colors.primaryText,
                    dimen.shape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.padding(dimen.paddingS),
                text = "$reportsCount",
                style = typography.h4.copy(
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.width(dimen.padding2Xs))

        Icon(
            modifier = Modifier.rotate(180f).size(dimen.sizeM),
            imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
            tint = colors.tintColor,
            contentDescription = stringResource(R.string.icon)
        )
    }
}