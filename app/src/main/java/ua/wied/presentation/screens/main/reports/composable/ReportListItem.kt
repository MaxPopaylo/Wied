package ua.wied.presentation.screens.main.reports.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.InstructionItem
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick

@Composable
fun ReportListItem(
    modifier: Modifier = Modifier,
    instruction: Instruction,
    reportsCount: Int,
    reportsIconOnClick: () -> Unit,
    createIconOnClick: () -> Unit
) {
    InstructionItem(
        modifier = modifier,
        instruction = instruction,
        actions = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(40.dp)
                        .bounceClick{if (reportsCount > 0) reportsIconOnClick()}
                        .border(
                            1.25.dp,
                            colors.tintColor,
                            dimen.shape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(dimen.paddingXs),
                        text = "$reportsCount",
                        style = typography.h1
                    )
                }

                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(40.dp)
                        .bounceClick(createIconOnClick)
                        .background(
                            colors.tintColor,
                            dimen.shape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(dimen.sizeS),
                        painter = painterResource(R.drawable.icon_plus),
                        contentDescription = "Plus",
                        tint = Color.White
                    )
                }
            }
        }
    )
}
