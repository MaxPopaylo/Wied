package ua.wied.presentation.common.composable.dropdowns

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.presentation.common.composable.BaseBottomSheet
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.hideBottomSheet
import ua.wied.presentation.common.utils.extensions.showBottomSheet
import ua.wied.presentation.screens.instructions.composable.InstructionEmptyScreen
import ua.wied.presentation.screens.instructions.composable.InstructionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionsDropdown(
    modifier: Modifier = Modifier,
    instructions: List<Instruction>,
    title: String? = null,
    minHeight: Dp? = null,
    initInstruction: Instruction? = null,
    loadInstructions: () -> Unit = {},
    onSelected: (Instruction) -> Unit = {}
) {
    val heightModifier = if (minHeight != null) Modifier.heightIn(min = minHeight)
    else Modifier

    var selectedInstruction by remember { mutableStateOf(initInstruction) }

    val menuText = if (selectedInstruction != null) {
        selectedInstruction!!.title
    } else {
        stringResource(R.string.choose_instruction)
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(initInstruction) {
        selectedInstruction = initInstruction
    }

    Column(modifier) {
        title?.let {
            Text(
                text = it,
                style = typography.body1,
                color = colors.secondaryText
            )
        }

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .then(heightModifier)
                .clip(dimen.shape)
                .background(
                    colors.secondaryBackground,
                    dimen.shape
                )
                .clickable{
                    showBottomSheet(coroutineScope, bottomSheetState) {
                        loadInstructions()
                        showBottomSheet = true
                    }
                }
                .padding(
                    vertical = dimen.paddingM,
                    horizontal = dimen.paddingXl
                )
            ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = menuText,
                style = typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .padding(
                        end = dimen.paddingM
                    )
                    .rotate(
                        if (!showBottomSheet) 0f else 180f
                    ),
                imageVector = ImageVector.vectorResource(R.drawable.icon_dropdown_arrow),
                contentDescription = "Arrow",
                tint = colors.primaryText
            )
        }
    }

    if (showBottomSheet) {
        InstructionsBottomSheet (
            instructions = instructions,
            onClose = {
                hideBottomSheet(coroutineScope, bottomSheetState) {
                    showBottomSheet = false
                }
            },
            instructionChosen = {
                selectedInstruction = it
                onSelected(it)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionsBottomSheet(
    instructions: List<Instruction>,
    onClose: () -> Unit,
    instructionChosen: (Instruction) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredInstructions = remember(searchQuery, instructions) {
        if (searchQuery.isBlank()) {
            instructions
        } else {
            val query = searchQuery.trim().lowercase()
            instructions.filter { it.title.lowercase().contains(query) }
        }
    }

    BaseBottomSheet(
        onClose = onClose,
        cancelButtonText = stringResource(R.string.cancel),
        header = {
            Text(
                text = stringResource(R.string.instructions),
                style = typography.h4
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.75f)
                .padding(bottom = dimen.paddingXl),
            verticalArrangement = Arrangement.spacedBy(dimen.paddingL)
        ) {
            SearchField(
                modifier = Modifier.padding(top = dimen.paddingM),
                text = searchQuery,
                onSearchValueChange = { searchQuery = it }
            )

            if (filteredInstructions.isEmpty()) {
                InstructionEmptyScreen(
                    isManager = false,
                    isFiltered = searchQuery.isNotEmpty(),
                    onCreationClick = {}
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(dimen.paddingM)
                ) {
                    items(filteredInstructions) { instruction ->
                        InstructionItem(
                            instruction = instruction,
                            onClick = {
                                instructionChosen(instruction)
                                onClose()
                            }
                        )
                    }
                }
            }
        }
    }
}