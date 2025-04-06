package ua.wied.presentation.screens.main.instructions.composable

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.wied.R
import ua.wied.presentation.common.composable.PrimaryTextButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun InstructionEmptyScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(40.dp),
            painter = painterResource(R.drawable.icon_camcorder),
            tint = colors.primaryText,
            contentDescription = "Camcorder"
        )
        Text(
            modifier = Modifier.padding(top = 4.dp),
            text = stringResource(R.string.no_instructions),
            style = typography.w500.copy(fontSize = 16.sp),
            color = colors.primaryText
        )
        PrimaryTextButton(
            title = stringResource(R.string.create),
            onClick = {}
        )
    }
}