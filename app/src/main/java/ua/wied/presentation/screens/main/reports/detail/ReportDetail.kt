package ua.wied.presentation.screens.main.reports.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ua.wied.R
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.formatToShortDate

@Composable
fun ReportDetailScreen(
    report: Report
) {
    Column(
        modifier = Modifier
            .padding(top = dimen.containerPaddingLarge)
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
                    text = report.author.name,
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

        Spacer(modifier = Modifier.height(dimen.paddingExtraLarge))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colors.secondaryBackground,
                    dimen.shape
                )
                .padding(dimen.paddingS)
        ) {
            Text(
                text = report.info,
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 18.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(dimen.paddingXl))

        Column(
            modifier = Modifier
                .background(
                    colors.secondaryBackground,
                    dimen.shape
                )
                .padding(dimen.paddingL)
        ){
            PhotoGrid(report.imageUrls.map { it.imageUrl })
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
            contentPadding = PaddingValues(dimen.zero),
            horizontalArrangement = Arrangement.spacedBy(dimen.paddingS),
            verticalArrangement = Arrangement.spacedBy(dimen.paddingS)
        ) {
            items(imageUrls.size) { index ->
                AsyncImage(
                    model = imageUrls[index],
                    contentDescription = stringResource(R.string.placeholder),
                    placeholder = painterResource(R.drawable.img_placeholder),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 2.5f)
                        .clip(dimen.shape)
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
