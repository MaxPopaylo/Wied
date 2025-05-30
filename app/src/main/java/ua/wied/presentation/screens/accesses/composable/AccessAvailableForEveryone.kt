package ua.wied.presentation.screens.accesses.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ua.wied.R
import ua.wied.presentation.common.composable.PrimaryTextButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun AccessAvailableForEveryone(
    onAddAccessClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(dimen.sizeL),
            imageVector = ImageVector.vectorResource(R.drawable.icon_people),
            tint = colors.primaryText,
            contentDescription = "People"
        )
        Text(
            modifier = Modifier.padding(top = dimen.padding2Xs),
            text = stringResource(R.string.available_to_everyone),
            style = typography.body1
        )
        PrimaryTextButton(
            title = stringResource(R.string.add_access),
            onClick = onAddAccessClick
        )
    }
}