package ua.wied.presentation.screens.instructions.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun InstructionDetail(
    instruction: Instruction
) {
    Column(
        modifier = Modifier.padding(top = dimen.containerPaddingLarge)
    ) {

        Text(
            text = stringResource(R.string.title),
            style = typography.body3,
            color = colors.secondaryText
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colors.secondaryBackground,
                    dimen.shape
                )
                .padding(dimen.paddingS),
            text = instruction.title,
            style = typography.body1
        )

        Text(
            modifier = Modifier.padding(top = dimen.paddingM),
            text = stringResource(R.string.photo),
            style = typography.body3,
            color = colors.secondaryText
        )


    }

}