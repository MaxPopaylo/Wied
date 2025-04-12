package ua.wied.presentation.common.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import ua.wied.R

data class WiEDColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val errorColor: Color,
    val starColor: Color
)

data class WiEDTypography(
    val h1: TextStyle,
    val h2: TextStyle,
    val h3: TextStyle,
    val h4: TextStyle,
    val h5: TextStyle,
    val h6: TextStyle,
    val h7: TextStyle,
    val body1: TextStyle,
    val body2: TextStyle,
    val body3: TextStyle,
    val body4: TextStyle,
    val button1: TextStyle,
    val button2: TextStyle,
    val button3: TextStyle
)

data class WiEDDimension(
    val shape: RoundedCornerShape,

    val zero: Dp,
    val one: Dp,

    val padding2Xs: Dp,
    val paddingXs: Dp,
    val paddingS: Dp,
    val paddingM: Dp,
    val paddingL: Dp,
    val paddingXl: Dp,
    val padding2Xl: Dp,
    val padding3Xl: Dp,

    val paddingLarge: Dp,
    val paddingExtraLarge: Dp,
    val containerPadding: Dp,
    val containerPaddingLarge: Dp,

    val sizeS: Dp,
    val sizeM: Dp,
    val sizeL: Dp
)

object WiEDFonts {
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

    internal val dimen: WiEDDimension
        @Composable @ReadOnlyComposable get() = LocalWiEDDimension.current

}

internal val LocalWiEDColors = staticCompositionLocalOf<WiEDColors> {
    error("no colors provided")
}

internal val LocalWiEDTypography = staticCompositionLocalOf<WiEDTypography> {
    error("no text styles provided")
}

internal val LocalWiEDDimension = staticCompositionLocalOf<WiEDDimension> {
    error("no dimensions provided")
}

