package ua.wied.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick
import kotlin.math.roundToInt

@Stable
@Composable
fun ActionIcon(
    onClick: () -> Unit,
    backgroundColor: Color,
    title: String,
    icon: Painter,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    tint: Color = Color.White
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable(
                onClick = onClick,
                indication = ripple(color = backgroundColor.copy(alpha = 1.5f)),
                interactionSource = interactionSource
            )
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = dimen.paddingXl),
            painter = icon,
            contentDescription = contentDescription,
            tint = tint
        )
        Text(
            modifier = Modifier.padding(horizontal = dimen.paddingXl),
            text = title,
            color = tint,
            style = typography.w400.copy(fontSize = 12.sp)
        )
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@Stable
@Composable
fun SwipeToReveal(
    modifier: Modifier = Modifier,
    cornerShape: RoundedCornerShape = dimen.shape,
    threshold: Float = 0.3f,
    backgroundColorBehind: Color = Color.LightGray,
    onClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit,
    content: @Composable () -> Unit,
) {
    var actionsWidth by remember { mutableStateOf(0.dp) }
    val localDensity = LocalDensity.current

    val swipeableState = rememberSwipeableState(initialValue = 0)

    val anchors = remember(actionsWidth) {
        if (actionsWidth == 0.dp) {
            mapOf(0f to 0)
        } else {
            mapOf(0f to 0, -actionsWidth.value * localDensity.density to 1)
        }
    }

    val measureActions = remember {
        Modifier.onGloballyPositioned { coordinates ->
            with(localDensity) {
                actionsWidth = coordinates.size.width.toDp()
            }
        }
    }

    val offset = remember {
        Modifier.offset {
            IntOffset(swipeableState.offset.value.roundToInt(), 0)
        }
    }

    val clickable = remember {
        if (onClick != null) {
            Modifier.bounceClick(onClick)
        } else {
            Modifier
        }
    }

    Box(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .then(clickable)
            .clip(cornerShape)
            .background(backgroundColorBehind)
    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(0.99f)
                .align(Alignment.CenterEnd)
                .then(measureActions),
            horizontalArrangement = Arrangement.End,
            content = {
                actions()
            }
        )

        Box(
            modifier = Modifier
                .then(offset)
                .clip(cornerShape)
                .swipeable(
                    state = swipeableState,
                    anchors = anchors,
                    thresholds = { _, _ -> FractionalThreshold(threshold) },
                    orientation = Orientation.Horizontal,
                    resistance = null
                )
        ) {
            content()
        }
    }
}