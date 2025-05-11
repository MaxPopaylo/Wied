package ua.wied.presentation.screens.accesses.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.folder.Folder
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.composable.ActionIcon
import ua.wied.presentation.common.composable.SwipeToReveal
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun FolderListItem(
    modifier: Modifier = Modifier,
    folder: Folder<Instruction>,
    isManager: Boolean,
    onDelete: (Int) -> Unit
) {
    if (isManager) {
        SwipeToReveal(
            actions = {
                ActionIcon(
                    backgroundColor = colors.errorColor,
                    icon = ImageVector.vectorResource(R.drawable.icon_filled_delete),
                    tint = Color.White,
                    title = stringResource(R.string.delete),
                    onClick = { onDelete(folder.id) }
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
                    .padding(vertical = 16.dp, horizontal = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = dimen.paddingS)
                        .weight(1f),
                    text = folder.title,
                    style = typography.h5,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Box(modifier = Modifier.padding(start = dimen.paddingS)) {
                    Icon(
                        modifier = Modifier.rotate(180f).size(dimen.sizeM),
                        imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
                        tint = colors.tintColor,
                        contentDescription = stringResource(R.string.icon)
                    )
                }
            }
        }
    } else {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    colors.secondaryBackground,
                    dimen.shape
                )
                .padding(vertical = 16.dp, horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(start = dimen.paddingS)
                    .weight(1f),
                text = folder.title,
                style = typography.h5,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Box(modifier = Modifier.padding(start = dimen.paddingS)) {
                Icon(
                    modifier = Modifier.rotate(180f).size(dimen.sizeM),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
                    tint = colors.tintColor,
                    contentDescription = stringResource(R.string.icon)
                )
            }
        }
    }
}