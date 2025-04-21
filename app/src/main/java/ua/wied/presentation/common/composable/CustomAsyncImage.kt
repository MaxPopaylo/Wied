package ua.wied.presentation.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.fallback
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme.dimen

@Composable
fun CustomAsyncImage(
    modifier: Modifier = Modifier,
    posterUrl: String?,
    width: Dp? = null,
    height: Dp? = null,
    shape: Shape = dimen.shape,
    @DrawableRes placeholderRes: Int = R.drawable.img_placeholder,
    @StringRes contentDescRes: Int = R.string.placeholder,
) {
    val sizeModifier = when {
        width != null && height != null -> { Modifier.size(width = width, height = height) }
        else -> Modifier
    }

    val imageModifier = modifier
        .then(sizeModifier)
        .clip(shape)

    val placeholderPainter = painterResource(placeholderRes)
    val contentDescription = stringResource(contentDescRes)

    AsyncImage(
        modifier = imageModifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(posterUrl)
            .fallback(placeholderRes)
            .error(placeholderRes)
            .build(),
        placeholder = placeholderPainter,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
    )
}