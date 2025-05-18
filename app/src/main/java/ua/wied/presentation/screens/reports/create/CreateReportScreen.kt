package ua.wied.presentation.screens.reports.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.SquareIconButton
import ua.wied.presentation.common.composable.ImagePickerButton
import ua.wied.presentation.screens.instructions.composable.InstructionItem
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.UnderlineTextField
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.reports.create.models.CreateReportEvent
import androidx.core.net.toUri
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.reports.create.models.CreateReportState

@Composable
fun CreateReportScreen(
    instruction: Instruction,
    state: CreateReportState,
    onEvent: (CreateReportEvent) -> Unit,
    isFieldsEmpty: () -> Boolean,
    navigateToReports: () -> Unit
) {
    val isEnabled = isFieldsEmpty()

    LaunchedEffect(state.createResult) {
        state.createResult.collect { result ->
            result?.fold(
                onSuccess = { navigateToReports() },
                onFailure = {

                }
            )
        }
    }

    Column(
        modifier = Modifier
            .padding(top = dimen.containerPaddingLarge)
    ) {
        InstructionItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimen.paddingL),
            instruction = instruction,
            actions = {}
        )

        Text(
            modifier = Modifier.padding(vertical = dimen.paddingLarge),
            text = stringResource(R.string.report) + ":",
            style = typography.h4
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colors.secondaryBackground,
                    dimen.shape
                )
                .padding(
                    start = dimen.padding3Xl,
                    end = dimen.padding3Xl,
                    top = dimen.paddingXl,
                    bottom = dimen.paddingLarge
                ),
            verticalArrangement = Arrangement.spacedBy(dimen.padding2Xs)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    UnderlineTextField(
                        text = state.title,
                        title = stringResource(R.string.title) + ":",
                        maxTextLength = 20,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = { value ->
                            onEvent(CreateReportEvent.TitleChanged(value))
                        }
                    )

                    UnderlineTextField(
                        text = state.description,
                        title = stringResource(R.string.description) + ":",
                        maxTextLength = 2040,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = { value ->
                            onEvent(CreateReportEvent.DescriptionChanged(value))
                        }
                    )
                }

                SquareIconButton(
                    modifier = Modifier.padding(bottom = dimen.paddingL),
                    icon = ImageVector.vectorResource(R.drawable.icon_microphone),
                    backgroundColor = Color.Transparent,
                    borderColor = Color.Transparent,
                    iconColor = colors.tintColor,
                    onClick = {}
                )
            }

            Text(
                text = stringResource(R.string.photo) + ":",
                color = colors.secondaryText,
                style = typography.body2,
                modifier = Modifier.padding(bottom = dimen.paddingL)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(dimen.paddingL)) {

                repeat(4) { index ->
                    ImagePickerButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(dimen.shape),
                        shape = dimen.shape,
                        imageUri = state.imageUris.getOrNull(index)?.toUri(),
                        onImageChosen = { url ->
                            onEvent(CreateReportEvent.PhotoAdded(index, url))
                        },
                        onDeleteImage = { url ->
                            onEvent(CreateReportEvent.PhotoDeleted(url))
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(dimen.paddingExtraLarge))

        PrimaryButton(
            isEnabled = isEnabled,
            title = stringResource(R.string.send),
            onClick = {
                onEvent(CreateReportEvent.Create(instruction.id))
            }
        )
    }
}