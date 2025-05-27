package ua.wied.presentation.screens.ai_instruction.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.wied.R
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.ai_instruction.create.model.CreateAiRequestEvent
import ua.wied.presentation.screens.ai_instruction.create.model.CreateAiRequestState

@Composable
fun CreateAiRequestScreen(
    state: CreateAiRequestState,
    onEvent: (CreateAiRequestEvent) -> Unit,
    backToInstructions: (Boolean) -> Unit
) {
    val isButtonEnabled =
            state.jobPosition.isNotEmpty() &&
            state.workTask.isNotEmpty() &&
            state.businessType.isNotEmpty()

    LaunchedEffect(state.createResult) {
        state.createResult.collect { result ->
            result?.fold(
                onSuccess = { backToInstructions(true) },
                onFailure = {

                }
            )
        }
    }

    ContentBox (
        state = state
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(top = dimen.containerPaddingLarge),
            verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
        ) {
            DetailTextField(
                title = stringResource(R.string.business_type),
                text = state.businessType,
                isEditing = true,
                onTextChange = {
                    onEvent(CreateAiRequestEvent.OnBusinessTypeChanged(it))
                }
            )

            DetailTextField(
                title = stringResource(R.string.job_position),
                text = state.jobPosition,
                isEditing = true,
                onTextChange = {
                    onEvent(CreateAiRequestEvent.OnJobPositionChanged(it))
                }
            )

            DetailTextField(
                title = stringResource(R.string.work_task),
                text = state.workTask,
                isEditing = true,
                onTextChange = {
                    onEvent(CreateAiRequestEvent.OnWorkTaskChanged(it))
                }
            )

            DetailTextField(
                title = stringResource(R.string.additional_notes),
                text = state.additionalInfo,
                isEditing = true,
                onTextChange = {
                    onEvent(CreateAiRequestEvent.OnAdditionalInfoChanged(it))
                }
            )

            Text(
                text = stringResource(R.string.ai_request_description),
                style = typography.body1,
                color = colors.secondaryText
            )

            Spacer(Modifier.weight(1f))

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimen.padding2Xl),
                title = stringResource(R.string.create),
                onClick = {
                    onEvent(CreateAiRequestEvent.Create)
                },
                isEnabled = isButtonEnabled
            )
        }
    }
}