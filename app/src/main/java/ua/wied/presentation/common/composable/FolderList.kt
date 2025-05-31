package ua.wied.presentation.common.composable

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
import androidx.compose.ui.draganddrop.mimeTypes
import ua.wied.domain.models.DragAndDropItem
import ua.wied.domain.models.HasId
import ua.wied.domain.models.folder.Folder
import ua.wied.presentation.common.theme.WiEDTheme
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.utils.ItemTransferData
import ua.wied.presentation.common.utils.ItemTransferData.Companion.createTransferData
import ua.wied.presentation.common.utils.ItemTransferData.Companion.extractDragData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T : HasId> FolderList (
    modifier: Modifier = Modifier,
    folders: List<Folder<T>>,
    skipEmptyFolders: Boolean = true,
    itemView: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
    ) {
        folders.forEach { folder ->
            stickyHeader(key = "${folder.id}") {
                if (!(skipEmptyFolders && folder.items.isEmpty())) {
                    FolderListHeader(text = folder.title)
                }
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
    isManager: Boolean = true,
    skipEmptyFolders: Boolean = true,
    onItemDropped: (itemId: Int, targetFolderId: Int, newOrderNum: Int) -> Unit = { _, _, _ -> },
    onItemClick: (T) -> Unit,
    itemView: @Composable (Modifier, T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
    ) {
        folders.forEach { folder ->
            stickyHeader(key = "folder-${folder.id}") {
                if (!(skipEmptyFolders && folder.items.isEmpty())) {
                    FolderListHeader(
                        text = folder.title,
                        isDragAndDrop = true,
                        onDrop = { sourceFolderId, itemId ->
                            val targetOrder = folder.items.firstOrNull()?.orderNum ?: 0
                            onItemDropped(itemId, folder.id, targetOrder)
                        }
                    )
                }

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
                                            onItemDropped(
                                                data.itemId,
                                                folder.id,
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
                                    if (isManager) {
                                        val data = ItemTransferData(
                                            itemId = item.id,
                                            sourceFolderId = folder.id
                                        )
                                        startTransfer(
                                            transferData = createTransferData(data)
                                        )
                                    }
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
                            data.sourceFolderId?.let { onDrop(it, data.itemId) }
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