package ua.wied.presentation.screens.accesses.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.folder.Access
import ua.wied.presentation.common.composable.ActionIcon
import ua.wied.presentation.common.composable.SuccessDialog
import ua.wied.presentation.common.composable.SwipeToReveal
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun AccessListItem (
    modifier: Modifier = Modifier,
    access: Access,
    onClick: (Access) -> Unit = {},
    onDelete: (Int) -> Unit
) {
    var showConfirmDialog by remember { mutableStateOf(false) }

    SwipeToReveal(
        actions = {
            ActionIcon(
                backgroundColor = colors.errorColor,
                icon = ImageVector.vectorResource(R.drawable.icon_filled_delete),
                tint = Color.White,
                title = stringResource(R.string.delete),
                onClick = { showConfirmDialog = true }
            )
        },
        onClick = null
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    colors.secondaryBackground,
                    dimen.shape
                )
                .clickable {
                    onClick(access)
                }
                .padding(vertical = 16.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = dimen.paddingS)
                    .weight(1f),
                text = access.name,
                style = typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


    if (showConfirmDialog) {
        SuccessDialog(
            isDelete = true,
            onDismiss = {
                showConfirmDialog = false
            },
            onSuccess = { onDelete(access.id) }
        )
    }
}