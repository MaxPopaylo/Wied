package ua.wied.presentation.screens.ai_instruction.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.ai.AiResponse
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.DateFormats
import ua.wied.presentation.common.utils.Formatter

@Composable
fun AiInstructionListItem(
    response: AiResponse
) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth().padding(top = dimen.paddingM),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = dimen.shape,
            colors = CardDefaults.cardColors(containerColor = colors.secondaryBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimen.paddingM, vertical = dimen.paddingS),
                verticalArrangement = Arrangement.spacedBy(dimen.paddingS)
            ) {
                Text(
                    text = stringResource(R.string.ai_request),
                    style = typography.h6
                )

                Text(
                    text = listOfNotNull(
                        response.businessType,
                        response.jobPosition,
                        response.workTask,
                        response.additionalInfo
                    ).joinToString(separator = ", "),
                    style = typography.body1
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth().padding(vertical = dimen.paddingM),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            shape = dimen.shape,
            colors = CardDefaults.cardColors(containerColor = colors.secondaryBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimen.paddingM, vertical = dimen.paddingS),
                verticalArrangement = Arrangement.spacedBy(dimen.paddingS)
            ) {
                Text(
                    text = stringResource(R.string.ai_response),
                    style = typography.h6
                )

                Text(
                    text = response.aiResponse,
                    style = typography.body1
                )

                Text(
                    text = Formatter.formatDate(response.createdAt, DateFormats.FULL_DATE_TIME),
                    textAlign = TextAlign.End,
                    style = typography.h6,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}