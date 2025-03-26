package ua.wied.presentation.screens.main.reports.status_list

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.IconButton
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun ReportStatusListScreen(
    navController: NavHostController,
    instruction: Instruction,
    viewModel: ReportStatusListViewModel = hiltViewModel()
) {
    val todoReports by viewModel.todoReports.collectAsState()
    val inProgressReports by viewModel.inProgressReports.collectAsState()
    val doneReports by viewModel.doneReports.collectAsState()

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

        ReportsByStatusItem(
            modifier = Modifier,
            title = stringResource(R.string.new_reports),
            reportsCount = todoReports?.size ?: 0,
            itemClick = {
                navController.navigate(ReportNav.ReportsByStatusList(
                    reports = todoReports ?: emptyList(),
                    instruction = instruction
                ))
            }
        )

        ReportsByStatusItem(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.in_progress_reports),
            reportsCount = inProgressReports?.size ?: 0,
            itemClick = {
                navController.navigate(ReportNav.ReportsByStatusList(
                    reports = inProgressReports ?: emptyList(),
                    instruction = instruction
                ))
            }
        )

        ReportsByStatusItem(
            modifier = Modifier.padding(top = 24.dp),
            title = stringResource(R.string.done_reports),
            reportsCount = doneReports?.size ?: 0,
            itemClick = {
                navController.navigate(ReportNav.ReportsByStatusList(
                    reports = doneReports ?: emptyList(),
                    instruction = instruction
                ))
            }
        )
    }
}

@Composable
private fun ReportsByStatusItem(
    modifier: Modifier = Modifier,
    title: String,
    reportsCount: Int,
    itemClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { itemClick() }
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

        IconButton(
            modifier = Modifier.rotate(180f),
            icon = painterResource(R.drawable.icon_arrow_back),
            backgroundColor = Color.Transparent,
            iconColor = colors.primaryText,
            borderColor = Color.Transparent,
            onClick = {
                itemClick()
            }
        )
    }
}