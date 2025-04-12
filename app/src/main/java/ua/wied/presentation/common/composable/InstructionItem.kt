package ua.wied.presentation.common.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick

@Composable
fun InstructionItem(
    modifier: Modifier = Modifier,
    instruction: Instruction,
    onClick: (() -> Unit)? = null,
    actions: @Composable () -> Unit = { EmptyAction() }
) {

    val clickableModifier = if (onClick != null) {
        modifier.bounceClick(onClick)
    } else {
        modifier
    }

    Row(
        modifier = clickableModifier
            .fillMaxWidth()
            .background(
                colors.secondaryBackground,
                dimen.shape
            )
            .padding(vertical = 16.dp, horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (instruction.posterUrl.isNullOrEmpty()) {
            Image(
                modifier = Modifier
                    .height(40.dp)
                    .width(45.dp)
                    .clip(dimen.shape),
                painter = painterResource(R.drawable.img_placeholder),
                contentDescription = stringResource(R.string.placeholder),
                contentScale = ContentScale.Crop,
            )
        } else {
            AsyncImage(
                modifier = Modifier
                    .height(40.dp)
                    .width(45.dp)
                    .clip(dimen.shape),
                model = instruction.posterUrl,
                placeholder = painterResource(R.drawable.img_placeholder),
                contentDescription = stringResource(R.string.placeholder),
                contentScale = ContentScale.Crop,
            )
        }

        Text(
            modifier = Modifier
                .padding(start = dimen.paddingS)
                .weight(1f),
            text = instruction.title,
            color = colors.primaryText,
            style = typography.w500.copy(fontSize = 18.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Box(modifier = Modifier.padding(start = dimen.paddingS)) {
            actions()
        }
    }
}

@Composable
private fun EmptyAction() {
    Icon(
        modifier = Modifier.rotate(180f).size(dimen.sizeM),
        painter = painterResource(R.drawable.icon_arrow_back),
        tint = colors.tintColor,
        contentDescription = stringResource(R.string.icon)
    )
}
