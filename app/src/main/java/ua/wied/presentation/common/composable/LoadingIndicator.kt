package ua.wied.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ua.wied.presentation.common.theme.WiEDTheme.colors

@Composable
fun LoadingIndicator(
    isDarkened: Boolean = true
) {
    Box(
        Modifier
            .fillMaxSize()
            .background(
                if (isDarkened) Color(0x80000000) else Color.Transparent
            ),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = colors.tintColor,
            strokeWidth = 5.dp
        )
    }
}