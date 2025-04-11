package ua.wied.presentation.common.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.wied.domain.models.HasId
import ua.wied.domain.models.folder.Folder
import ua.wied.presentation.common.theme.WiEDTheme
import ua.wied.presentation.common.theme.WiEDTheme.colors

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
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        folders.forEach { folder ->
            stickyHeader(key = "${folder.id}") {
                FolderListHeader(folder.title)
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

@Composable
private fun FolderListHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    val typography = WiEDTheme.typography
    val headerTextStyle = remember { typography.w500.copy(fontSize = 20.sp) }

    Text(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.primaryBackground)
            .padding(horizontal = 0.dp, vertical = 8.dp),
        text = text,
        color = colors.primaryText,
        style = headerTextStyle
    )
}