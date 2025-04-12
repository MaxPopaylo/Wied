package ua.wied.presentation.common.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
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
        fontFamily = WiEDFonts.mariupolFamily,
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

    val dimension = WiEDDimension(
        shape = RoundedCornerShape(8.dp),

        zero = 0.dp,
        one = 1.dp,

        padding2Xs = 4.dp,
        paddingXs = 6.dp,
        paddingS = 8.dp,
        paddingM = 10.dp,
        paddingL = 12.dp,
        paddingXl = 14.dp,
        padding2Xl = 16.dp,
        padding3Xl = 18.dp,

        paddingLarge = 24.dp,
        paddingExtraLarge = 32.dp,
        containerPadding = 18.dp,
        containerPaddingLarge = 24.dp,

        sizeS = 15.dp,
        sizeM = 25.dp,
        sizeL = 40.dp
    )

    CompositionLocalProvider(
        LocalWiEDColors provides colors,
        LocalWiEDTypography provides typography,
        LocalWiEDDimension provides dimension,
        content = content
    )
}