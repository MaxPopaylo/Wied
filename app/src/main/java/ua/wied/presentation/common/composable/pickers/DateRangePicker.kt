package ua.wied.presentation.common.composable.pickers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import ua.wied.R
import ua.wied.domain.models.evaluation.DateRange
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.RoundIconButton
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(
    modifier: Modifier = Modifier,
    dateRange: DateRange,
    onClearFilter: () -> Unit,
    onApply: (DateRange) -> Unit
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }

    val state = rememberDateRangePickerState(
        initialSelectedStartDateMillis = dateRange.startDate.takeIf { it != null && it > 0 },
        initialSelectedEndDateMillis = dateRange.endDate.takeIf { it != null && it > 0 }
            ?.minus(86400000L)
    )

    RoundIconButton(
        modifier = modifier,
        icon = ImageVector.vectorResource(R.drawable.icon_calendar),
        onClick = {
            showDatePickerDialog = !showDatePickerDialog
        }
    )

    if (showDatePickerDialog) {
        DatePickerDialog(
            shape = dimen.shape,
            colors = DatePickerDefaults.colors(
                containerColor = Color.White
            ),
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {

            },
            dismissButton = {

            }
        ) {
            Column {
                DateRangePicker(
                    modifier = Modifier.weight(1f),
                    state = state,
                    showModeToggle = false,
                    colors = DatePickerDefaults.colors(
                        containerColor = Color.White,
                        selectedDayContentColor = Color.White,
                        selectedDayContainerColor = colors.tintColor,
                        dayInSelectionRangeContainerColor = colors.secondaryBackground,
                        dayInSelectionRangeContentColor = colors.primaryText,
                        todayDateBorderColor = colors.tintColor,
                        dayContentColor = colors.primaryText,
                        titleContentColor = colors.primaryText
                    )
                )

                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimen.containerPadding),
                    onClick = {
                        showDatePickerDialog = false
                        onApply(
                            DateRange(
                                startDate = state.selectedStartDateMillis ?: 0L,
                                endDate = state.selectedEndDateMillis?.plus(86400000L) ?: 0L
                            )
                        )
                    },
                    title = stringResource(R.string.select)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = dimen.containerPadding,
                            vertical = dimen.paddingXs
                        )
                ) {
                    SecondaryButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            onClearFilter()
                            state.setSelection(null, null)
                            showDatePickerDialog = false
                        },
                        title = stringResource(R.string.clear)
                    )

                    SecondaryButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = dimen.paddingXs),
                        onClick = { showDatePickerDialog = false },
                        title = stringResource(R.string.cancel)
                    )
                }
            }
        }
    }
}