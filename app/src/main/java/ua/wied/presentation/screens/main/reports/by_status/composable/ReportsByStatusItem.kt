package ua.wied.presentation.screens.main.reports.by_status.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import ua.wied.R
import ua.wied.domain.models.report.Report
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick

@Composable
fun ReportsByStatusItem(
    modifier: Modifier = Modifier,
    report: Report,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(dimen.shape)
            .bounceClick(onClick)
            .background(
                color = colors.secondaryBackground,
                shape = dimen.shape
            )
            .padding(vertical = dimen.paddingM)
            .padding(start = dimen.paddingXl),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = report.author.name,
                style = typography.h5.copy(
                    fontWeight = FontWeight.W400
                )
            )
            Text(
                text = report.title,
                style = typography.h5
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            modifier = Modifier.rotate(180f).size(dimen.sizeM),
            painter = painterResource(R.drawable.icon_arrow_back),
            tint = colors.tintColor,
            contentDescription = stringResource(R.string.icon)
        )
    }
}