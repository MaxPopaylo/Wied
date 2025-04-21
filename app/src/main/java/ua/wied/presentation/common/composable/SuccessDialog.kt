package ua.wied.presentation.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun SuccessDialog(
    onDismiss: () -> Unit,
    onSuccess: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(dimen.paddingS)
        ) {
            Text(
                modifier = Modifier.padding(top = dimen.paddingS),
                text = stringResource(R.string.confirm_changes_message),
                style = typography.h5
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(dimen.paddingS)
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