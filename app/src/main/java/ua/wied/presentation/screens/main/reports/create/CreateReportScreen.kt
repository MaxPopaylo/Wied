package ua.wied.presentation.screens.main.reports.create

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.IconButton
import ua.wied.presentation.common.composable.ImagePickerButton
import ua.wied.presentation.common.composable.InstructionItem
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.UnderlineTextField
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.main.reports.create.models.CreateReportEvents

@Composable
fun CreateReportScreen(
    instruction: Instruction,
    viewModel: CreateReportViewModel = hiltViewModel()
) {

    val isEnabled = viewModel.isFieldsEmpty()
    val roundedShape = RoundedCornerShape(4.dp)

    Column(
        modifier = Modifier
            .padding(top = 24.dp)
    ) {
        InstructionItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            instruction = instruction,
            actions = {}
        )

        Text(
            modifier = Modifier.padding(vertical = 24.dp),
            text = stringResource(R.string.report) + ":",
            color = colors.primaryText,
            style = typography.w400.copy(
                fontSize = 20.sp
            )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colors.secondaryBackground,
                    roundedShape
                )
                .padding(
                    start = 18.dp,
                    end = 18.dp,
                    top = 14.dp,
                    bottom = 24.dp
                ),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    UnderlineTextField(
                        text = viewModel.state.title,
                        title = stringResource(R.string.title) + ":",
                        maxTextLength = 20,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = { value ->
                            viewModel.onEvent(CreateReportEvents.TitleChanged(value))
                        }
                    )

                    UnderlineTextField(
                        text = viewModel.state.description,
                        title = stringResource(R.string.description) + ":",
                        maxTextLength = 2040,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        onValueChange = { value ->
                            viewModel.onEvent(CreateReportEvents.DescriptionChanged(value))
                        }
                    )
                }

                IconButton(
                    modifier = Modifier.padding(bottom = 12.dp),
                    icon = painterResource(R.drawable.icon_microphone),
                    backgroundColor = Color.Transparent,
                    borderColor = Color.Transparent,
                    iconColor = colors.tintColor,
                    onClick = {}
                )
            }

            Text(
                text = stringResource(R.string.photo) + ":",
                color = colors.secondaryText,
                style = typography.w400.copy(
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                repeat(4) { index ->
                    ImagePickerButton(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .clip(roundedShape),
                        shape = roundedShape,
                        imageUri = viewModel.state.imgUrls.getOrNull(index)?.let { Uri.parse(it) },
                        onImageChosen = { url ->
                            viewModel.onEvent(CreateReportEvents.PhotoAdded(index, url))
                        },
                        onDeleteImage = { url ->
                            viewModel.onEvent(CreateReportEvents.PhotoDeleted(url))
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            isEnabled = isEnabled,
            title = stringResource(R.string.send),
            onClick = {}
        )
    }
}