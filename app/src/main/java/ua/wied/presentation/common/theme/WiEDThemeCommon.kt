package ua.wied.presentation.common.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import ua.wied.R

data class WiEDColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tertiaryText: Color,
    val tintColor: Color,
    val errorColor: Color,
    val starColor: Color
)

data class WiEDTypography(
    val w300: TextStyle,
    val w400: TextStyle,
    val w500: TextStyle,
    val w600: TextStyle,
    val w700: TextStyle
)

object WiEDFonts {
    val robotoFamily: FontFamily by lazy {
        FontFamily(
            Font(R.font.font_roboto)
        )
    }
    val mariupolFamily: FontFamily by lazy {
        FontFamily(
            Font(R.font.font_mariupol_regular, FontWeight.W400),
            Font(R.font.font_mariupol_medium, FontWeight.W500),
            Font(R.font.font_mariupol_bold, FontWeight.W700)
        )
    }
}

object WiEDTheme {

    internal val colors: WiEDColors
        @Composable @ReadOnlyComposable get() = LocalWiEDColors.current

    internal val typography: WiEDTypography
        @Composable @ReadOnlyComposable get() = LocalWiEDTypography.current

}

internal val LocalWiEDColors = staticCompositionLocalOf<WiEDColors> {
    error("no colors provided")
}

internal val LocalWiEDTypography = staticCompositionLocalOf<WiEDTypography> {
    error("no text styles provided")
}

