package ua.wied.presentation.common.composable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.video.VideoFrameDecoder
import coil3.video.videoFrameMillis
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography


@Composable
fun ImagePickerButton(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape,
    onImageChosen: (String) -> Unit,
    onDeleteImage: (String) -> Unit,
    imageUri: Uri?
) {

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        onImageChosen(uri.toString())
    }

    Box(
        modifier = modifier
            .heightIn(40.dp)
            .background(
                colors.primaryBackground,
                shape
            )
            .aspectRatio(1f / 1f)
            .clickable {
                if (imageUri == null) {
                    launcher.launch("image/*")
                } else {
                    onDeleteImage(imageUri.toString())
                }
            },
        contentAlignment = Alignment.Center
    ) {

        imageUri?.let {
            Box(modifier = modifier.fillMaxSize()) {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(colors.tintColor.copy(alpha = 0.5f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_close),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(0.4f),
                        contentScale = ContentScale.Crop,
                        colorFilter = ColorFilter.tint(Color.White)
                    )
                }
            }

        } ?: run {
            Image(
                modifier = Modifier.fillMaxSize(0.5f).wrapContentSize(),
                imageVector = ImageVector.vectorResource(R.drawable.icon_add_photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(colors.primaryText)
            )
        }
    }
}

@Composable
fun LargeImagePicker(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = dimen.shape,
    isEditing: Boolean = false,
    onImageChosen: (String) -> Unit,
    onDeleteImage: (String) -> Unit,
    onViewClick: (String) -> Unit = {},
    imageUri: Uri?
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        onImageChosen(uri.toString())
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                colors.primaryBackground,
                shape
            )
            .aspectRatio(1f/ .5f)
            .clickable {
                if (isEditing) {
                    if (imageUri == null) {
                        launcher.launch("image/*")
                    } else {
                        onDeleteImage(imageUri.toString())
                    }
                } else {
                    onViewClick(imageUri.toString())
                }
            },
        contentAlignment = Alignment.Center
    ) {

        imageUri?.let {
            Box(modifier = modifier.fillMaxSize()) {
                AsyncImage(
                    model = it,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                if (isEditing) {
                    Box(
                        modifier = Modifier
                            .matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(dimen.sizeL),
                            imageVector = ImageVector.vectorResource(R.drawable.icon_close),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(dimen.sizeL),
                            imageVector = ImageVector.vectorResource(R.drawable.icon_view_photo),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }

        } ?: run {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(dimen.paddingS)
            ) {
                Text(
                    text = stringResource(R.string.upload_image),
                    style = typography.h5.copy(fontSize = 16.sp),
                    color = colors.secondaryText
                )

                Image(
                    modifier = Modifier.size(dimen.sizeL),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_add_photo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(colors.primaryText)
                )
            }
        }
    }
}

@Composable
fun LargeVideoPicker(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = dimen.shape,
    isEditing: Boolean = false,
    onVideoChosen: (String) -> Unit,
    onDeleteVideo: (String) -> Unit,
    onViewClick: (String) -> Unit = {},
    videoUri: Uri?
) {
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onVideoChosen(it.toString()) }
    }

    val videoLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }
        .build()

    val request = ImageRequest.Builder(context)
        .data(videoUri)
        .videoFrameMillis(0)
        .build()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                colors.primaryBackground,
                shape
            )
            .aspectRatio(1f/ .5f)
            .clickable {
                if (isEditing) {
                    if (videoUri == null) {
                        launcher.launch("video/*")
                    } else {
                        onDeleteVideo(videoUri.toString())
                    }
                } else {
                    videoUri?.let { onViewClick(it.toString()) }
                }
            },
        contentAlignment = Alignment.Center
    ) {

        videoUri?.let {
            Box(modifier = modifier.fillMaxSize()) {
                AsyncImage(
                    model = request,
                    imageLoader = videoLoader,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                if (isEditing) {
                    Box(
                        modifier = Modifier
                            .matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(dimen.sizeL),
                            imageVector = ImageVector.vectorResource(R.drawable.icon_close),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .matchParentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier.size(dimen.sizeL),
                            imageVector = ImageVector.vectorResource(R.drawable.icon_view_video),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                    }
                }
            }

        } ?: run {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(dimen.paddingS)
            ) {
                Text(
                    text = stringResource(R.string.upload_video),
                    style = typography.h5.copy(fontSize = 16.sp),
                    color = colors.secondaryText
                )

                Image(
                    modifier = Modifier.size(dimen.sizeL),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_add_video),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(colors.primaryText)
                )
            }
        }
    }
}