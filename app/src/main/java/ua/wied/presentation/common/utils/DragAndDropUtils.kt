package ua.wied.presentation.common.utils


import android.content.ClipData
import android.content.ClipDescription
import androidx.compose.ui.draganddrop.DragAndDropEvent
import androidx.compose.ui.draganddrop.DragAndDropTransferData
import androidx.compose.ui.draganddrop.toAndroidDragEvent
import org.json.JSONObject

data class ItemTransferData(
    val itemId: Int,
    val sourceFolderId: Int? = null
) {
    fun toJson(): String = JSONObject().apply {
        put(KEY_ITEM_ID, itemId)
        sourceFolderId?.let { put(KEY_SOURCE, it) }
    }.toString()

    companion object {
        const val MIME_TYPE = "application/vnd.wied.item-transfer"
        private const val KEY_ITEM_ID = "itemId"
        private const val KEY_SOURCE = "sourceFolderId"

        fun fromJson(json: String): ItemTransferData? = try {
            JSONObject(json).let {
                ItemTransferData(
                    itemId = it.getInt(KEY_ITEM_ID),
                    sourceFolderId = if (it.has(KEY_SOURCE)) it.getInt(KEY_SOURCE) else null
                )
            }
        } catch (e: Exception) {
            null
        }

        fun createTransferData(itemTransferData: ItemTransferData): DragAndDropTransferData {
            val jsonData = itemTransferData.toJson()
            val description = ClipDescription(
                MIME_TYPE,
                arrayOf(MIME_TYPE)
            )
            val item = ClipData.Item(jsonData)
            val clipData = ClipData(description, item)
            return DragAndDropTransferData(clipData = clipData)
        }

        fun extractDragData(event: DragAndDropEvent): ItemTransferData? {
            val androidEvent = event.toAndroidDragEvent()
            val clipData = androidEvent.clipData ?: return null
            return if (clipData.description.hasMimeType(MIME_TYPE)) {
                clipData.getItemAt(0).text?.toString()?.let { fromJson(it) }
            } else null
        }
    }
}
