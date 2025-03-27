package ua.wied.presentation.common.composable

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import ua.wied.R
import ua.wied.presentation.common.theme.WiEDTheme.colors


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
                        painter = painterResource(R.drawable.icon_close),
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
                painter = painterResource(R.drawable.icon_add_photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(colors.primaryText)
            )
        }
    }
}