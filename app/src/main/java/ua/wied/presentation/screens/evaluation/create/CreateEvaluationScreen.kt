package ua.wied.presentation.screens.evaluation.create

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.instruction.Instruction
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.dropdowns.EmployeesDropdown
import ua.wied.presentation.common.composable.dropdowns.InstructionsDropdown
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.dropdowns.DateDropdown
import ua.wied.presentation.common.composable.rating_star.RatingBar
import ua.wied.presentation.common.composable.rating_star.RatingBarStyle
import ua.wied.presentation.common.composable.rating_star.StepSize
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.extensions.positionAwareImePadding
import ua.wied.presentation.common.utils.extensions.toEvaluation
import ua.wied.presentation.screens.evaluation.create.model.CreateEvaluationEvent
import ua.wied.presentation.screens.evaluation.create.model.CreateEvaluationState

@Composable
fun CreateEvaluationScreen(
    employee: User? = null,
    instruction: Instruction? = null,
    state: CreateEvaluationState,
    onEvent: (CreateEvaluationEvent) -> Unit,
    backToEvaluations: (Boolean) -> Unit
) {
    val scrollState = rememberScrollState()
    val isButtonEnabled = state.instruction != null &&
                          state.employee != null &&
                          state.itemsEvaluation.isNotEmpty()

    LaunchedEffect(employee, instruction) {
        employee?.let { onEvent(CreateEvaluationEvent.OnEmployeeChanged(it)) }
        instruction?.let { onEvent(CreateEvaluationEvent.OnInstructionChanged(it)) }
    }

    LaunchedEffect(state.createResult) {
        state.createResult.collect { result ->
            result?.fold(
                onSuccess = { backToEvaluations(true) },
                onFailure = {

                }
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .positionAwareImePadding()
            .verticalScroll(scrollState)
            .padding(top = dimen.containerPaddingLarge),
        verticalArrangement = Arrangement.spacedBy(dimen.paddingXl)
    ) {
        EmployeesDropdown(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.employee),
            employees = state.employees,
            initEmployee = state.employee,
            loadEmployees = {
                onEvent(CreateEvaluationEvent.LoadEmployees)
            },
            onSelected = {
                onEvent(CreateEvaluationEvent.OnEmployeeChanged(it))
            }
        )

        InstructionsDropdown(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.instruction),
            instructions = state.instructions,
            initInstruction = state.instruction,
            loadInstructions = {
                onEvent(CreateEvaluationEvent.LoadInstructions)
            },
            onSelected = {
                onEvent(CreateEvaluationEvent.OnInstructionChanged(it))
            }
        )

        DateDropdown(
            modifier = Modifier.fillMaxWidth(),
            title = stringResource(R.string.date_picker_title),
            selectedDate = state.createdAt,
            onApply = { date ->
                onEvent(CreateEvaluationEvent.OnDateChanged(date))
            }
        )

        AnimatedVisibility(
            visible = state.instruction != null,
            enter = expandVertically(),
            exit = shrinkVertically()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.padding(bottom = dimen.paddingS),
                    text = stringResource(R.string.instruction_items_evaluations),
                    style = typography.body1,
                    color = colors.secondaryText
                )

                if (state.itemsEvaluation.isEmpty()) {
                    ItemsEmpty()
                } else {
                    state.itemsEvaluation.forEach { item ->
                        ElementEvaluationRow(
                            modifier = Modifier
                                .padding(
                                    vertical = dimen.padding2Xs,
                                    horizontal = dimen.paddingM
                                ),
                            elementId = item.elementId,
                            elementTitle = item.title,
                            initEvaluation = item.evaluation.toEvaluation(),
                            onRatingChanged = { elementId, ratingValue ->
                                onEvent(CreateEvaluationEvent.OnItemEvaluationChanged(elementId, ratingValue))
                            }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimen.paddingM)
                        .padding(horizontal = dimen.paddingM)
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = state.finalEvaluation.toString(),
                        style = typography.h3,
                        color = colors.tintColor
                    )

                    RatingBar(
                        modifier = Modifier.padding(start = dimen.paddingM),
                        value = state.finalEvaluation.toFloat(),
                        stepSize = StepSize.HALF,
                        style = RatingBarStyle.Fill(
                            activeColor = colors.starColor
                        ),
                        size = dimen.sizeM,
                        onValueChange = {},
                        onRatingChanged = {}
                    )
                }
            }
        }

        DetailTextField(
            modifier = Modifier.padding(top = dimen.paddingS),
            title = stringResource(R.string.description),
            text = state.info,
            isEditing = true,
            minHeight = 120.dp,
            onTextChange = { value ->
                onEvent(CreateEvaluationEvent.OnInfoChanged(value))
            }
        )

        Spacer(Modifier.height(dimen.paddingXl))

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = dimen.padding2Xl),
            title = stringResource(R.string.create),
            onClick = {
                onEvent(CreateEvaluationEvent.Create)
            },
            isEnabled = isButtonEnabled
        )
    }
}

@Composable
private fun ItemsEmpty(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(vertical = 64.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(dimen.sizeM),
            imageVector = ImageVector.vectorResource(R.drawable.icon_star),
            tint = colors.primaryText,
            contentDescription = "Evaluations"
        )
        Text(
            modifier = Modifier.padding(top = dimen.paddingS),
            text = stringResource(R.string.no_instruction_items),
            style = typography.body1
        )
    }
}

@Composable
private fun ElementEvaluationRow(
    modifier: Modifier = Modifier,
    elementTitle: String,
    elementId: Int,
    initEvaluation: Float = 0f,
    onRatingChanged: (Int, Float) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimen.paddingXs),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = elementTitle,
            style = typography.body1,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        RatingBar(
            value = initEvaluation,
            stepSize = StepSize.HALF,
            style = RatingBarStyle.Fill(
                activeColor = colors.starColor
            ),
            size = 18.dp,
            onValueChange = {
                onRatingChanged(elementId, it)
            },
            onRatingChanged = {}
        )
    }
}