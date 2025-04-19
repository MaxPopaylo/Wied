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
        color = colors.primaryText,
        letterSpacing = 0.5.sp
    )
    val typography = WiEDTypography(
        h1 = baseTextStyle.copy(
            fontWeight = FontWeight.W500,
            fontSize = 24.sp
        ),
        h2 = baseTextStyle.copy(
            fontWeight = FontWeight.W700,
            fontSize = 22.sp
        ),
        h3 = baseTextStyle.copy(
            fontWeight = FontWeight.W500,
            fontSize = 22.sp
        ),
        h4 = baseTextStyle.copy(
            fontWeight = FontWeight.W500,
            fontSize = 20.sp
        ),
        h5 = baseTextStyle.copy(
            fontWeight = FontWeight.W500,
            fontSize = 18.sp
        ),
        h6 = baseTextStyle.copy(
            fontWeight = FontWeight.W700,
            fontSize = 12.sp
        ),
        h7 = baseTextStyle.copy(
            fontWeight = FontWeight.W400,
            fontSize = 12.sp
        ),
        body1 = baseTextStyle.copy(
            fontWeight = FontWeight.W400,
            fontSize = 16.sp
        ),
        body2 = baseTextStyle.copy(
            fontWeight = FontWeight.W400,
            fontSize = 14.sp
        ),
        body3 = baseTextStyle.copy(
            fontWeight = FontWeight.W500,
            fontSize = 14.sp
        ),
        body4 = baseTextStyle.copy(
            fontWeight = FontWeight.W400,
            fontSize = 12.sp
        ),
        button1 = baseTextStyle.copy(
            fontWeight = FontWeight.W700,
            fontSize = 18.sp
        ),
        button2 = baseTextStyle.copy(
            fontWeight = FontWeight.W700,
            fontSize = 16.sp
        ),
        button3 = baseTextStyle.copy(
            fontWeight = FontWeight.W400,
            fontSize = 16.sp
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
        topBarPadding = 4.5.dp,

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