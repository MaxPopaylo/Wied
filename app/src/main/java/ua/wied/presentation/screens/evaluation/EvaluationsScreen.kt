package ua.wied.presentation.screens.evaluation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.FolderList
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.evaluation.model.EvaluationsEvent
import ua.wied.presentation.screens.evaluation.model.EvaluationsState
import ua.wied.presentation.screens.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.instructions.composable.InstructionItem

@Composable
fun EvaluationsScreen(
    state: EvaluationsState,
    onEvent: (EvaluationsEvent) -> Unit,
    navigateToEvaluationList: (Instruction) -> Unit
) {
    Column(
        modifier = Modifier.padding(bottom = dimen.paddingS),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
    ) {
        SearchField(
            text = state.search,
            onSearchValueChange = {
                onEvent(EvaluationsEvent.SearchChanged(it))
            }
        )
        ContentBox(
            state = state,
            onRefresh = { onEvent(EvaluationsEvent.Refresh) },
            emptyScreen = {
                InstructionEmptyScreen(
                    isManager = false,
                    isFiltered = state.search.isNotEmpty(),
                    onCreationClick = {}
                )
            }
        ) {
            FolderList (
                folders = state.folders,
                itemView = {
                    InstructionItem(
                        modifier = Modifier.fillMaxWidth(),
                        instruction = it,
                        onClick = {
                            navigateToEvaluationList(it)
                        },
                        actions = {
                            Box(
                                modifier = Modifier
                                    .height(40.dp)
                                    .widthIn(40.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    modifier = Modifier.size(dimen.sizeM),
                                    imageVector = ImageVector.vectorResource(R.drawable.icon_star_filled),
                                    contentDescription = "Star",
                                    tint = colors.starColor
                                )
                            }
                        }
                    )
                }
            )
        }
    }
}