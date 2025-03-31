package ua.wied.presentation.screens.main.reports.by_status.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.wied.R
import ua.wied.domain.models.report.Report
import ua.wied.presentation.common.composable.IconButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun ReportsByStatusItem(
    modifier: Modifier = Modifier,
    report: Report,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .background(
                color = colors.secondaryBackground,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(vertical = 10.dp)
            .padding(start = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = report.userId.toString(),
                color = colors.primaryText,
                style = typography.w400.copy(
                    fontSize = 18.sp
                )
            )
            Text(
                text = report.title,
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 18.sp
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            modifier = Modifier.rotate(180f),
            icon = painterResource(R.drawable.icon_arrow_back),
            backgroundColor = Color.Transparent,
            iconColor = colors.primaryText,
            borderColor = Color.Transparent,
            onClick = onClick
        )
    }
}