package ua.wied.presentation.screens.main.reports.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ua.wied.R
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.domain.models.user.Role
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.theme.WiEDTheme
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.formatToShortDate
import java.time.LocalDateTime

@Composable
fun ReportDetailScreen(
    report: Report
) {
    Column(
        modifier = Modifier
            .padding(top = 24.dp)
    ) {
        Row {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = report.title,
                    color = colors.primaryText,
                    style = typography.w500.copy(
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = report.creator.name,
                    color = colors.primaryText,
                    style = typography.w400.copy(
                        fontSize = 18.sp
                    )
                )
            }


            Text(
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .weight(1f),
                text = report.createTime.formatToShortDate(),
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 16.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Column(
            modifier = Modifier
                .background(
                    colors.secondaryBackground,
                    RoundedCornerShape(4.dp)
                )
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "Info: ",
                color = colors.tintColor,
                style = typography.w500.copy(
                    fontSize = 18.sp
                )
            )
            Text(
                text = report.info,
                color = colors.primaryText,
                style = typography.w400.copy(
                    fontSize = 18.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Column(
            modifier = Modifier
                .background(
                    colors.secondaryBackground,
                    RoundedCornerShape(4.dp)
                )
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(
                text = "Photos: ",
                color = colors.tintColor,
                style = typography.w500.copy(
                    fontSize = 18.sp
                )
            )

            PhotoGrid(report.imageUrls)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    text = getStatusMessage(report.status),
                    color = colors.tintColor,
                    style = typography.w700.copy(
                        fontSize = 25.sp
                    )
                )
                Text(
                    text = report.updateTime.formatToShortDate(),
                    color = colors.primaryText,
                    style = typography.w500.copy(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun PhotoGrid(imageUrls: List<String>) {
    if (imageUrls.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(0.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(imageUrls.size) { index ->
                AsyncImage(
                    model = imageUrls[index],
                    contentDescription = stringResource(R.string.placeholder),
                    placeholder = painterResource(R.drawable.img_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 2f)
                        .clip(RoundedCornerShape(4.dp))
                        .border(
                            1.5.dp,
                            colors.tintColor,
                            RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }
}

@Composable
private fun getStatusMessage(status: ReportStatus) =
    when(status) {
        ReportStatus.TODO -> stringResource(R.string.todo_report_message)
        ReportStatus.IN_PROGRESS -> stringResource(R.string.in_progress_reports)
        ReportStatus.DONE -> stringResource(R.string.done_report_message)
    }

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReportDetailScreenPrew() {
    WiEDTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primaryBackground)
                .padding(horizontal = 24.dp)
                .padding(top = 52.dp)
        ) {
            ReportDetailScreen(
                Report(
                    id = 3,
                    instructionId = 1,
                    title = "Test 3",
                    info = "testtesttesttesttesttesttesttesttesttesttestvtesttest",
                    imageUrls = listOf("", ""),
                    createTime = LocalDateTime.now(),
                    updateTime = LocalDateTime.now(),
                    status = ReportStatus.IN_PROGRESS,
                    creator = User(
                        1,
                        "",
                        "Max",
                        "",
                        "",
                        "",
                        "",
                        Role.EMPLOYEE
                    )
                )
            )
        }
    }
}