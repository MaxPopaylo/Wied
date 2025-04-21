package ua.wied.presentation.common.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.fallback
import coil3.video.VideoFrameDecoder
import coil3.video.videoFrameMillis
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick

@Composable
fun MediaGrid(
    modifier: Modifier = Modifier,
    urls: List<String>,
    gridItem: @Composable (String, Int) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxWidth(),
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(dimen.zero),
        horizontalArrangement = Arrangement.spacedBy(dimen.paddingS),
        verticalArrangement = Arrangement.spacedBy(dimen.paddingS)
    ) {
        items(urls.size) { index ->
            gridItem(urls[index], index)
        }
    }
}

@Composable
fun GridPhotoItem(
    modifier: Modifier = Modifier,
    imgUrl: String?,
    isEditing: Boolean = false,
    shape: Shape = dimen.shape,
    iconSize: Dp = dimen.sizeL,
    aspectRatio: Float = 4f / 2.5f,
    @DrawableRes placeholderRes: Int = R.drawable.img_placeholder,
    @StringRes contentDescRes: Int = R.string.placeholder,
    onDeleteClick: (String) -> Unit = {},
    onViewClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .clip(shape)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imgUrl)
                .fallback(placeholderRes)
                .error(placeholderRes)
                .build(),
            contentDescription = stringResource(contentDescRes),
            placeholder = painterResource(placeholderRes),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .bounceClick {
                    if (isEditing) onDeleteClick(imgUrl ?: "")
                    else onViewClick(imgUrl ?: "")
                }
                .clip(shape)
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = .4f), shape),
            contentAlignment = Alignment.Center
        ) {
            Image(
                imageVector = if (isEditing) ImageVector.vectorResource(R.drawable.icon_close)
                else ImageVector.vectorResource(R.drawable.icon_view_photo),
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
    }
}

@Composable
fun GridVideoItem(
    modifier: Modifier = Modifier,
    videoUrl: String?,
    title: String? = null,
    isEditing: Boolean = false,
    shape: Shape = dimen.shape,
    iconSize: Dp = dimen.sizeM,
    aspectRatio: Float = 4f / 2.5f,
    @DrawableRes placeholderRes: Int = R.drawable.img_placeholder,
    @StringRes contentDescRes: Int = R.string.placeholder,
    onDeleteClick: (String) -> Unit = {},
    onViewClick: (String) -> Unit
) {
    val context = LocalContext.current

    val videoImageLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()

    val request = ImageRequest.Builder(context)
        .data(videoUrl)
        .videoFrameMillis(0)
        .fallback(placeholderRes)
        .error(placeholderRes)
        .build()

    Box(
        modifier = modifier
            .aspectRatio(aspectRatio)
            .clip(shape)
    ) {
        AsyncImage(
            model = request,
            imageLoader = videoImageLoader,
            contentDescription = stringResource(contentDescRes),
            placeholder = painterResource(placeholderRes),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .bounceClick {
                    if (isEditing) onDeleteClick(videoUrl ?: "")
                    else onViewClick(videoUrl ?: "")
                }
                .clip(shape)
        )

        Column (
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = .4f), shape),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                imageVector = if (isEditing) ImageVector.vectorResource(R.drawable.icon_close)
                else ImageVector.vectorResource(R.drawable.icon_view_video),
                contentDescription = null,
                modifier = Modifier.size(iconSize),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(Color.White)
            )

            if (!isEditing) {
                title?.let{
                    Text(
                        modifier = Modifier.padding(top = dimen.paddingS),
                        text = it,
                        style = typography.h6,
                        color = Color.White
                    )
                }
            }
        }
    }
}