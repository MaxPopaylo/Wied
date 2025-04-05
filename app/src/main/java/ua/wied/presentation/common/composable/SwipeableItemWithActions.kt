package ua.wied.presentation.common.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ua.wied.domain.models.HasId
import ua.wied.presentation.common.theme.WiEDTheme.typography
import kotlin.math.roundToInt

@Composable
fun SwipeableItemWithActions(
    modifier: Modifier = Modifier,
    isRevealed: Boolean,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit,
    content: @Composable () -> Unit
) {
    var contextMenuWidth by remember { mutableFloatStateOf(0f) }

    val offset = remember { Animatable(initialValue = 0f) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = isRevealed, key2 = contextMenuWidth) {
        if (isRevealed) {
            offset.animateTo(
                targetValue = -contextMenuWidth,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
            )
        } else {
            offset.animateTo(
                targetValue = 0f,
                animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .onSizeChanged { contextMenuWidth = it.width.toFloat() }
                .clip(RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, bottomEnd = 4.dp, topEnd = 4.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions()
        }

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .offset { IntOffset(offset.value.roundToInt(), 0) }
                .pointerInput(contextMenuWidth) {
                    detectHorizontalDragGestures(
                        onHorizontalDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffset = (offset.value + dragAmount)
                                    .coerceIn(-contextMenuWidth, 0f)
                                offset.snapTo(newOffset)
                            }
                        },
                        onDragEnd = {
                            when {
                                offset.value <= -contextMenuWidth / 2f -> {
                                    scope.launch {
                                        offset.animateTo(
                                            targetValue = -contextMenuWidth,
                                            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                                        )
                                        onExpanded()
                                    }
                                }
                                else -> {
                                    scope.launch {
                                        offset.animateTo(
                                            targetValue = 0f,
                                            animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
                                        )
                                        onCollapsed()
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            content()
        }
    }
}


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
    Column(
        modifier = modifier
            .fillMaxHeight()
            .clickable (
                onClick = onClick,
                indication = ripple(
                    color = backgroundColor.copy(alpha = 1.5f)
                ),
                interactionSource = remember { MutableInteractionSource() }
            )
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.padding(horizontal = 14.dp),
            painter = icon,
            contentDescription = contentDescription,
            tint = tint
        )
        Text(
            modifier = Modifier.padding(horizontal = 14.dp),
            text = title,
            color = tint,
            style = typography.w400.copy(fontSize = 12.sp)
        )
    }
}

@Stable
data class SwipeableItemState<T : HasId>(
    val item: T,
): HasId {
    override val id: Int
        get() = item.id

    var isRevealed: Boolean = false
}