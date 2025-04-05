package ua.wied.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick

@Composable
fun InstructionItem(
    modifier: Modifier = Modifier,
    instruction: Instruction,
    onClick: () -> Unit = {},
    actions: @Composable () -> Unit = { EmptyAction() }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .bounceClick(onClick)
            .background(
                colors.secondaryBackground,
                RoundedCornerShape(4.dp)
            )
            .padding(vertical = 16.dp, horizontal = 14.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .height(40.dp)
                .width(45.dp)
                .clip(RoundedCornerShape(4.dp)),
            model = instruction.posterUrl,
            placeholder = painterResource(R.drawable.img_placeholder),
            contentDescription = stringResource(R.string.placeholder),
            contentScale = ContentScale.Crop,
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = instruction.title,
            color = colors.primaryText,
            style = typography.w500.copy(fontSize = 20.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        actions()
    }
}

@Composable
private fun EmptyAction() {
    Icon(
        modifier = Modifier.rotate(180f).size(25.dp),
        painter = painterResource(R.drawable.icon_arrow_back),
        tint = colors.tintColor,
        contentDescription = stringResource(R.string.icon)
    )
}
