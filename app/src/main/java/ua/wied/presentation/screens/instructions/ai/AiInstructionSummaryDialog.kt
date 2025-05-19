package ua.wied.presentation.screens.instructions.ai

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ua.wied.R
import ua.wied.domain.models.settings.Language
import ua.wied.domain.models.settings.Settings
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.theme.WiEDTheme
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun AiInstructionSummaryDialog(
    onContinue: () -> Unit,
    onClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClose,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.primaryBackground)
                .padding(
                    start = dimen.containerPadding,
                    end = dimen.containerPadding,
                    top = dimen.containerPaddingLarge,
                    bottom = dimen.containerPadding
                ),
            verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(
                    onClick = onClose
                ) {
                    Icon(
                        modifier = Modifier
                            .size(dimen.sizeM),
                        imageVector = ImageVector.vectorResource(R.drawable.icon_close),
                        contentDescription = "close",
                        tint = colors.tintColor
                    )
                }
            }

            Text(
                text = stringResource(R.string.ai_assistant_title),
                style = typography.h1.copy(
                    fontWeight = FontWeight.W700
                )
            )

            Icon(
                modifier = Modifier
                    .padding(top = dimen.paddingM)
                    .size(dimen.sizeL),
                imageVector = ImageVector.vectorResource(R.drawable.icon_ai_brain),
                contentDescription = "close",
                tint = colors.tintColor
            )

            Text(
                text = stringResource(R.string.ai_assistant_description),
                style = typography.body1.copy(
                    fontWeight = FontWeight.W700
                )
            )

            Text(
                text = stringResource(R.string.ai_assistant_instruction),
                style = typography.body1
            )

            Spacer(Modifier.weight(1f))

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.create),
                onClick = {
                    onContinue()
                    onClose()
                }
            )
        }

    }
}

@Preview
@Composable
private fun AiInstructionSummaryDialogPreview() {
    WiEDTheme(Settings(Language.UKRAINIAN, false)) { AiInstructionSummaryDialog({}, {}) }
}