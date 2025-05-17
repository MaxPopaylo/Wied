package ua.wied.presentation.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun SuccessDialog(
    isDelete: Boolean = false,
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        ElevatedCard(
            colors = CardColors(
                containerColor = colors.primaryBackground,
                contentColor = colors.primaryText,
                disabledContainerColor = colors.primaryBackground,
                disabledContentColor = colors.primaryText
            )
        ) {
            Column(
                modifier = Modifier.padding(dimen.containerPadding),
                verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
            ) {
                Text(
                    text = if (isDelete) stringResource(R.string.confirm_delete_message)
                    else stringResource(R.string.confirm_changes_message),
                    style = typography.h5
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimen.paddingL)
                ) {
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.confirm_button),
                        onClick = {
                            onSuccess()
                            onDismiss()
                        }
                    )

                    SecondaryButton(
                        modifier = Modifier.weight(1f),
                        title = stringResource(R.string.cancel),
                        onClick = onDismiss
                    )
                }
            }
        }
    }
}
