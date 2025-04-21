package ua.wied.presentation.common.utils.extensions

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
inline fun hideBottomSheet(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    crossinline onCompletion: () -> Unit
) = scope.launch { bottomSheetState.hide() }.invokeOnCompletion { onCompletion() }

@OptIn(ExperimentalMaterial3Api::class)
inline fun showBottomSheet(
    scope: CoroutineScope,
    bottomSheetState: SheetState,
    crossinline onShow: () -> Unit
) = scope.launch {
    onShow()
    bottomSheetState.show()
}