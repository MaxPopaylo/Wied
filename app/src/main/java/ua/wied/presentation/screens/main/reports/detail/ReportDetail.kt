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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import ua.wied.R
import ua.wied.domain.models.report.Report
import ua.wied.domain.models.report.ReportStatus
import ua.wied.presentation.common.composable.FullScreenImageDialog
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick
import ua.wied.presentation.common.utils.extensions.formatToShortDate
import ua.wied.presentation.screens.main.reports.detail.models.ReportDetailEvent

@Composable
fun ReportDetailScreen(
    report: Report,
    viewModel: ReportDetailViewModel = hiltViewModel()
) {
    var choseImage by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(ReportDetailEvent.LoadData(report))
    }

    val state by viewModel.uiState.collectAsStateWithLifecycle()

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
                    style = typography.h4
                )
                Text(
                    text = report.author.name,
                    style = typography.h5.copy(
                        fontWeight = FontWeight.W400
                    )
                )
            }


            Text(
                modifier = Modifier
                    .wrapContentWidth(Alignment.End)
                    .weight(1f),
                text = report.createTime.formatToShortDate(),
                style = typography.h5
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
                style = typography.h5
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
            PhotoGrid(
                imageUrls = report.imageUrls.map { it.imageUrl },
                onClick = {
                    choseImage = it
                    showDialog = true
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            if (viewModel.isManager()) {
                ChangeStatusButtons(
                    currentStatus = state.report?.status ?: ReportStatus.TODO,
                    onEvent = viewModel::onEvent
                )
            } else {
                Column {
                    Text(
                        text = getStatusMessage(report.status),
                        color = colors.tintColor,
                        style = typography.h2
                    )
                    Text(
                        text = report.updateTime.formatToShortDate(),
                        style = typography.h5
                    )
                }
            }
        }
    }

    if (showDialog) {
        FullScreenImageDialog(
            url = choseImage,
            onDismissRequest = {
                choseImage = ""
                showDialog = false
            }
        )
    }
}

@Composable
private fun PhotoGrid(
    imageUrls: List<String>,
    onClick: (String) -> Unit
) {
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
                        .bounceClick {
                            onClick(imageUrls[index])
                        }
                )
            }
        }
    }
}

@Composable
private fun ChangeStatusButtons(
    currentStatus: ReportStatus,
    onEvent: (ReportDetailEvent) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(dimen.paddingS)
    ){
        val inProgressTitle = stringResource(R.string.in_progress_reports)
        val doneTitle = stringResource(R.string.done_reports)

        if (currentStatus == ReportStatus.IN_PROGRESS) {
            PrimaryButton(
                title = inProgressTitle,
                onClick = { onEvent(ReportDetailEvent.ChangeStatus(ReportStatus.IN_PROGRESS)) }
            )
        } else {
            SecondaryButton(
                title = inProgressTitle,
                onClick = { onEvent(ReportDetailEvent.ChangeStatus(ReportStatus.IN_PROGRESS)) }
            )
        }

        if (currentStatus == ReportStatus.DONE) {
            PrimaryButton(
                title = doneTitle,
                onClick = { onEvent(ReportDetailEvent.ChangeStatus(ReportStatus.DONE)) }
            )
        } else {
            SecondaryButton(
                title = doneTitle,
                onClick = { onEvent(ReportDetailEvent.ChangeStatus(ReportStatus.DONE)) }
            )
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
