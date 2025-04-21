package ua.wied.presentation.common.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import ua.wied.presentation.common.theme.WiEDTheme.dimen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
    onClose: () -> Unit,
    onConfirm: () -> Unit = {},
    onCancel: () -> Unit = onClose,
    isButtonEnabled: Boolean = true,
    submitButtonText: String? = null,
    cancelButtonText: String? = null,
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit
) {
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onClose,
        containerColor = Color.White
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = dimen.containerPaddingLarge)
                .padding(bottom = dimen.paddingLarge),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = {
                header()
                content()
                if (submitButtonText != null) {
                    PrimaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimen.paddingL),
                        onClick = onConfirm,
                        title = submitButtonText,
                        isEnabled = isButtonEnabled
                    )
                }
                if (cancelButtonText != null) {
                    SecondaryButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = dimen.paddingM),
                        onClick = onCancel,
                        title = cancelButtonText
                    )
                }
            }
        )
    }
}