package ua.wied.presentation.common.utils.extensions

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.findRootCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

fun Modifier.positionAwareImePadding() = composed {
    var consumePadding by remember { mutableIntStateOf(0) }
    this@positionAwareImePadding
        .onGloballyPositioned { coordinates ->
            val rootCoordinate = coordinates.findRootCoordinates()
            val bottom = coordinates.positionInWindow().y + coordinates.size.height

            consumePadding = (rootCoordinate.size.height - bottom).toInt()
        }
        .consumeWindowInsets(PaddingValues(bottom = consumePadding.pxToDp()))
        .imePadding()
}

@Composable
fun Int.pxToDp(): Dp {
    return with(LocalDensity.current) { this@pxToDp.toDp() }
}