package ua.wied.presentation.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun WiEDTheme(
    content: @Composable () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()

    val colors = when(darkTheme) {
        false -> defaultLightPalette
        else -> defaultLightPalette
    }

    val baseTextStyle = TextStyle(
        fontFamily = WiEDFonts.robotoFamily,
        letterSpacing = 0.5.sp
    )
    val typography = WiEDTypography(
        w300 = baseTextStyle.copy(
            fontWeight = FontWeight.W300
        ),
        w400 = baseTextStyle.copy(
            fontWeight = FontWeight.W400
        ),
        w500 = baseTextStyle.copy(
            fontWeight = FontWeight.W500
        ),
        w600 = baseTextStyle.copy(
            fontWeight = FontWeight.W600
        ),
        w700 = baseTextStyle.copy(
            fontWeight = FontWeight.W700
        )
    )

    CompositionLocalProvider(
        LocalWiEDColors provides colors,
        LocalWiEDTypography provides typography,
        content = content
    )
}