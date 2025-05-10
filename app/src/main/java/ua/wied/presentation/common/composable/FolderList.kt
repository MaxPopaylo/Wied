package ua.wied.presentation.common.composable

import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.draganddrop.dragAndDropSource
import androidx.compose.foundation.draganddrop.dragAndDropTarget
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTarget
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.mimeTypes
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import org.json.JSONObject
import ua.wied.domain.models.DragAndDropItem
import ua.wied.domain.models.HasId
import ua.wied.domain.models.folder.Folder
import ua.wied.presentation.common.theme.WiEDTheme
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : HasId> FolderList (
    modifier: Modifier = Modifier,
    folders: List<Folder<T>>,
    itemView: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
    ) {
        folders.forEach { folder ->
            stickyHeader(key = "${folder.id}") {
                FolderListHeader(text = folder.title)
            }
            items(
                folder.items,
                key = { "${folder.id}-${it.id}" }
            ) { item ->
                itemView(item)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : DragAndDropItem> DragAndDropFolderList(
    modifier: Modifier = Modifier,
    folders: List<Folder<T>>,
    onItemDropped: (item: T, targetFolderId: Int, newOrderNum: Int) -> Unit = { _, _, _ -> },
    onItemClick: (T) -> Unit,
    itemView: @Composable (Modifier, T) -> Unit
) {
    val allItemsById: Map<Int, T> = remember(folders) {
        folders.flatMap { it.items }.associateBy { it.id }
    }

    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
    ) {
        folders.forEach { folder ->
            stickyHeader(key = "folder-${folder.id}") {
                FolderListHeader(
                    text = folder.title,
                    isDragAndDrop = true,
                    onDrop = { sourceFolderId, itemId ->
                        val targetOrder = folder.items.lastOrNull()?.orderNum?.plus(1) ?: 0
                        val item = allItemsById[itemId] ?: return@FolderListHeader
                        onItemDropped(item, folder.id, targetOrder)
                    }
                )
            }
            items(
                folder.items,
                key = { "${folder.id}-${it.id}" }
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
                                            val droppedItem = allItemsById[data.itemId] ?: return true
                                            if (droppedItem != item) {
                                                onItemDropped(
                                                    droppedItem,
                                                    folder.id,
                                                    item.orderNum
                                                )
                                            }
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
                                        itemId = item.id,
                                        sourceFolderId = folder.id
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FolderListHeader(
    modifier: Modifier = Modifier,
    text: String,
    isDragAndDrop: Boolean = false,
    onDrop: (sourceFolderId: Int, itemId: Int) -> Unit = { _, _ -> }
) {
    val typography = WiEDTheme.typography
    val headerTextStyle = remember { typography.h4 }

    val dragAndDropModifier = if (isDragAndDrop) {
        Modifier.dragAndDropTarget(
            shouldStartDragAndDrop = { event ->
                event.mimeTypes().contains(ItemTransferData.MIME_TYPE)
            },
            target = remember {
                object : DragAndDropTarget {
                    override fun onDrop(event: DragAndDropEvent): Boolean {
                        extractDragData(event)?.let { data ->
                            onDrop(data.sourceFolderId, data.itemId)
                        }
                        return true
                    }
                }
            }
        )
    } else Modifier

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.primaryBackground)
            .then(dragAndDropModifier)
            .padding(horizontal = dimen.zero, vertical = dimen.paddingS)
    ) {
        Text(
            text = text,
            style = headerTextStyle
        )
    }

}

private data class ItemTransferData(
    val itemId: Int,
    val sourceFolderId: Int
) {
    companion object {
        const val MIME_TYPE = "application/vnd.wied.item-transfer"
        private const val KEY_ITEM_ID = "itemId"
        private const val KEY_SOURCE = "sourceFolderId"

        fun fromJson(json: String): ItemTransferData? = try {
            JSONObject(json).let {
                ItemTransferData(
                    itemId = it.getInt(KEY_ITEM_ID),
                    sourceFolderId = it.getInt(KEY_SOURCE)
                )
            }
        } catch (e: Exception) {
            null
        }
    }

    fun toJson(): String = JSONObject().apply {
        put(KEY_ITEM_ID, itemId)
        put(KEY_SOURCE, sourceFolderId)
    }.toString()
}

private fun createTransferData(itemTransferData: ItemTransferData): DragAndDropTransferData {
    val jsonData = itemTransferData.toJson()
    val description = ClipDescription(
        ItemTransferData.MIME_TYPE,
        arrayOf(ItemTransferData.MIME_TYPE)
    )
    val item = ClipData.Item(jsonData)
    val clipData = ClipData(description, item)
    return DragAndDropTransferData(clipData = clipData)
}

private fun extractDragData(event: DragAndDropEvent): ItemTransferData? {
    val androidEvent = event.toAndroidDragEvent()
    val clipData = androidEvent.clipData ?: return null
    return if (clipData.description.hasMimeType(ItemTransferData.MIME_TYPE)) {
        clipData.getItemAt(0).text?.toString()?.let { ItemTransferData.fromJson(it) }
    } else null
}
