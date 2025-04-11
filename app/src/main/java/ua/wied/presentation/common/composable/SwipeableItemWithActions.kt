package ua.wied.presentation.common.composable

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ua.wied.domain.models.HasId
import ua.wied.presentation.common.theme.WiEDTheme.typography
import kotlin.math.abs
import kotlin.math.roundToInt

private val SwipeAnimationSpec = tween<Float>(durationMillis = 300, easing = FastOutSlowInEasing)
private val ActionItemShape = RoundedCornerShape(topStart = 0.dp, bottomStart = 0.dp, bottomEnd = 4.dp, topEnd = 4.dp)


@Composable
fun SwipeableItem(
    modifier: Modifier = Modifier,
    isRevealed: Boolean,
    onExpanded: () -> Unit = {},
    onCollapsed: () -> Unit = {},
    actions: @Composable RowScope.() -> Unit,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    val controller = remember { SwipeableItemController(scope) }

    val measureActions = remember {
        Modifier.onGloballyPositioned { layoutCoordinates ->
            controller.updateActionsWidth(layoutCoordinates.size.width.toFloat())
        }
    }

    val offsetIntX by remember {
        derivedStateOf { IntOffset(controller.getOffsetX().roundToInt(), 0) }
    }

    LaunchedEffect(isRevealed) {
        controller.forceAnimateTo(isRevealed)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        SwipeableItemActions(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .then(measureActions),
            actions = actions
        )

        SwipeableItemContent(
            modifier = Modifier.fillMaxSize(),
            offsetIntX = offsetIntX,
            onDrag = { controller.handleDrag(it) },
            onDragStopped = { controller.handleDragEnd(it, onExpanded, onCollapsed) },
            content = content
        )
    }
}

@Composable
private fun SwipeableItemActions(
    modifier: Modifier,
    actions: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.clip(ActionItemShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        actions()
    }
}

@Composable
private fun SwipeableItemContent(
    modifier: Modifier,
    offsetIntX: IntOffset,
    onDrag: (Float) -> Unit,
    onDragStopped: (Float) -> Unit,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .offset { offsetIntX }
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta -> onDrag(delta) },
                onDragStopped = { velocity -> onDragStopped(velocity) }
            )
    ) {
        content()
    }
}

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

object SwipeableItemStatePool {
    private val pool = mutableMapOf<Int, SwipeableItemState<*>>()

    fun <T : HasId> getOrCreate(item: T): SwipeableItemState<T> {
        @Suppress("UNCHECKED_CAST")
        return pool.getOrPut(item.id) {
            SwipeableItemState(item)
        } as SwipeableItemState<T>
    }

    fun clear() {
        pool.clear()
    }
}

@Stable
private class SwipeableItemController(
    private val coroutineScope: CoroutineScope
) {
    private val offsetX = Animatable(0f)
    private var actionsWidth = 0f
    private var currentJob: Job? = null

    var isRevealed = false
        private set

    fun updateActionsWidth(width: Float) {
        if (width > 0f && abs(width - actionsWidth) > 0.1f) {
            actionsWidth = width
        }
    }

    fun getOffsetX(): Float = offsetX.value

    fun handleDrag(delta: Float) {
        coroutineScope.launch {
            val newValue = (offsetX.value + delta).coerceIn(-actionsWidth, 0f)
            offsetX.snapTo(newValue)
        }
    }

    fun handleDragEnd(velocity: Float, onExpanded: () -> Unit, onCollapsed: () -> Unit) {
        val thresholdRatio = if (abs(velocity) > 500) 0.25f else 0.5f
        val threshold = actionsWidth * thresholdRatio

        currentJob?.cancel()
        currentJob = coroutineScope.launch {
            try {
                if (abs(offsetX.value) > threshold) {

                    offsetX.animateTo(-actionsWidth, SwipeAnimationSpec)
                    isRevealed = true
                    onExpanded()
                } else {

                    offsetX.animateTo(0f, SwipeAnimationSpec)
                    isRevealed = false
                    onCollapsed()
                }
            } catch (_: CancellationException) { }
        }
    }

    fun forceAnimateTo(revealed: Boolean) {
        if (actionsWidth <= 0) return

        currentJob?.cancel()
        currentJob = coroutineScope.launch {
            try {
                val targetValue = if (revealed) -actionsWidth else 0f
                offsetX.animateTo(targetValue, SwipeAnimationSpec)
                isRevealed = revealed
            } catch (_: CancellationException) { }
        }
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