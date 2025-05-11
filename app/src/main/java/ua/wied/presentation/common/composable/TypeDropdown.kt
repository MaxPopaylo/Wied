package ua.wied.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.SelectableEnum
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick
import ua.wied.presentation.common.utils.extensions.hideBottomSheet
import ua.wied.presentation.common.utils.extensions.showBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T: SelectableEnum> TypeDropdown(
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    title: String? = null,
    minHeight: Dp? = null,
    types: List<T>,
    initType: T,
    onSelected: (T) -> Unit = {}
) {
    val heightModifier = if (minHeight != null) Modifier.heightIn(min = minHeight)
    else Modifier

    var selectedType by remember { mutableStateOf(initType) }

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    Column(modifier) {
        title?.let {
            Text(
                text = title,
                style = typography.body1,
                color = colors.secondaryText
            )
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(heightModifier)
                .clip(dimen.shape)
                .background(
                    colors.secondaryBackground,
                    dimen.shape
                )
                .clickable{
                    showBottomSheet(coroutineScope, bottomSheetState) {
                        showBottomSheet = true
                    }
                }
                .padding(
                    vertical = dimen.paddingM,
                    horizontal = dimen.paddingXl
                )
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(dimen.paddingS),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(dimen.sizeM)
                        .clip(dimen.shape),
                    painter = painterResource(selectedType.icon),
                    contentDescription = "Choose",
                    tint = iconColor
                )

                Text(
                    text = stringResource(selectedType.displayName),
                    style = typography.body1
                )
            }

            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(
                        end = dimen.paddingM
                    )
                    .rotate(
                        if (!showBottomSheet) 0f else 180f
                    ),
                imageVector = ImageVector.vectorResource(R.drawable.icon_dropdown_arrow),
                contentDescription = "Arrow",
                tint = colors.primaryText
            )
        }
    }

    if (showBottomSheet) {
        TypeDropdownBottomSheet(
            types = types,
            iconColor = iconColor,
            onClose = {
                hideBottomSheet(coroutineScope, bottomSheetState) {
                    showBottomSheet = false
                }
            },
            onChosen = {
                selectedType = it
                onSelected(it)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T : SelectableEnum> TypeDropdownBottomSheet(
    modifier: Modifier = Modifier,
    iconColor: Color = Color.Unspecified,
    types: List<T>,
    onChosen: (T) -> Unit,
    onClose: () -> Unit
) {
    BaseBottomSheet(
        modifier = modifier,
        onClose = onClose
    ) {
        Column(
            modifier = Modifier.padding(dimen.containerPadding),
            verticalArrangement = Arrangement.spacedBy(dimen.paddingXs)
        ) {
            types.forEach { type ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = colors.secondaryBackground,
                            shape = dimen.shape
                        )
                        .bounceClick {
                            onChosen(type)
                            onClose()
                        }
                        .padding(
                            vertical = dimen.paddingL,
                            horizontal = dimen.paddingL
                        ),
                    horizontalArrangement = Arrangement.spacedBy(dimen.paddingM),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .size(dimen.sizeM)
                            .clip(dimen.shape),
                        painter = painterResource(type.icon),
                        contentDescription = "Choose",
                        tint = iconColor
                    )
                    Text(
                        text = stringResource(type.displayName),
                        style = typography.button3
                    )
                }
            }

            Spacer(Modifier.height(dimen.paddingXl))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colors.secondaryBackground, shape = dimen.shape)
                    .bounceClick(onClose)
                    .padding(vertical = dimen.paddingXl),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.cancel), style = typography.button2)
            }
        }
    }
}

