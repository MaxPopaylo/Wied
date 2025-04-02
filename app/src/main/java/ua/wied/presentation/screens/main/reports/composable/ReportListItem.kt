package ua.wied.presentation.screens.main.reports.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.IconButton
import ua.wied.presentation.common.composable.InstructionItem
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography

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
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .widthIn(40.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .border(
                            1.25.dp,
                            colors.tintColor,
                            RoundedCornerShape(4.dp)
                        )
                        .clickable { reportsIconOnClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = "$reportsCount",
                        color = colors.primaryText,
                        style = typography.w500.copy(
                            fontSize = 24.sp
                        )
                    )
                }
                IconButton(
                    icon = painterResource(R.drawable.icon_plus),
                    backgroundColor = colors.tintColor,
                    iconColor = colors.tertiaryText,
                    borderColor = colors.tintColor,
                    onClick = createIconOnClick
                )
            }
        }
    )
}
