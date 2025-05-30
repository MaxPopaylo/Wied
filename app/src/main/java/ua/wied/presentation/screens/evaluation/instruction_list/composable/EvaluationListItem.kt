package ua.wied.presentation.screens.evaluation.instruction_list.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import ua.wied.R
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ua.wied.domain.models.evaluation.Evaluation
import ua.wied.domain.models.evaluation.ItemEvaluation
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.DateFormats
import ua.wied.presentation.common.utils.Formatter

@Composable
fun EvaluationListItem(
    modifier: Modifier = Modifier,
    isEmployeeEvaluation: Boolean = false,
    evaluation: Evaluation
) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded }
            .background(
                colors.secondaryBackground,
                dimen.shape
            ),
    ) {
        Column(
            modifier = Modifier
                .background(colors.secondaryBackground)
                .padding(vertical = 16.dp, horizontal = 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = Formatter.formatDate(evaluation.createTime, DateFormats.FULL_DATE),
                    style = typography.body1
                )

                Icon(
                    modifier = Modifier
                        .padding(end = dimen.paddingXs)
                        .size(dimen.sizeS)
                        .rotate(if(isExpanded) 180f else 0f),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_dropdown_arrow),
                    contentDescription = null,
                    tint = colors.primaryText
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimen.paddingS),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = if (isEmployeeEvaluation) evaluation.instructionTitle
                           else evaluation.employee.name,
                    style = typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                StarsRow(
                    modifier = Modifier.padding(horizontal = dimen.paddingM),
                    evaluation = evaluation.evaluation
                )
            }

            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .background(colors.secondaryBackground)
                        .padding(top = 16.dp)
                ) {
                    HorizontalDivider(
                        thickness = dimen.one,
                        color = colors.secondaryText
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Spacer(modifier = Modifier.height(8.dp))

                    evaluation.itemsEvaluation.forEach { item ->
                        ItemEvaluationRow(item)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun StarsRow(
    modifier: Modifier = Modifier,
    evaluation: Double
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimen.padding2Xs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        List(evaluation.toInt()) {
            Icon(
                modifier = Modifier
                    .padding(end = dimen.paddingXs)
                    .size(dimen.sizeS),
                imageVector = ImageVector.vectorResource(R.drawable.icon_star_filled),
                contentDescription = null,
                tint = colors.starColor
            )
        }
    }
}

@Composable
private fun ItemEvaluationRow(item: ItemEvaluation) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimen.paddingXs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = item.title,
            style = typography.body1
        )

        StarsRow(evaluation = item.evaluation)
    }
}
