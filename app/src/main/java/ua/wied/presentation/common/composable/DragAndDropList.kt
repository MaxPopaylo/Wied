package ua.wied.presentation.common.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.mimeTypes
import ua.wied.domain.models.DragAndDropItem
import ua.wied.domain.models.HasId
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.utils.ItemTransferData
import ua.wied.presentation.common.utils.ItemTransferData.Companion.createTransferData
import ua.wied.presentation.common.utils.ItemTransferData.Companion.extractDragData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : HasId> ItemList (
    modifier: Modifier = Modifier,
    reverseLayout: Boolean = false,
    items: List<T>,
    itemView: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = reverseLayout,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
    ) {
        items(
            items,
            key = { it.id }
        ) { item ->
            itemView(item)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : DragAndDropItem> DragAndDropItemList(
    modifier: Modifier = Modifier,
    items: List<T>,
    onItemDropped: (itemId: Int, newOrderNum: Int) -> Unit = { _, _ -> },
    onItemClick: (T) -> Unit,
    itemView: @Composable (Modifier, T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
    ) {
        items(
            items,
            key = { it.id }
        ) { item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .dragAndDropTarget(
                        shouldStartDragAndDrop = { event ->
                            event.mimeTypes().contains(ItemTransferData.MIME_TYPE)
                        },
                        target = remember {
                            object : DragAndDropTarget {
                                override fun onDrop(event: DragAndDropEvent): Boolean {
                                    extractDragData(event)?.let { data ->
                                        onItemDropped(
                                            data.itemId,
                                            item.orderNum
                                        )
                                    }
                                    return true
                                }
                            }
                        }
                    )
            ) {
                itemView(
                    Modifier.dragAndDropSource {
                        detectTapGestures(
                            onLongPress = {
                                val data = ItemTransferData(
                                    itemId = item.id
                                )
                                startTransfer(
                                    transferData = createTransferData(data)
                                )
                            },
                            onTap = { onItemClick(item) }
                        )
                    },
                    item
                )
            }
        }
    }
}