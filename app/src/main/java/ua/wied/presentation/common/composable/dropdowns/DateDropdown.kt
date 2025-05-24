package ua.wied.presentation.common.composable.dropdowns

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.presentation.common.composable.PrimaryButton
import ua.wied.presentation.common.composable.SecondaryButton
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.DateFormats
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDropdown(
    modifier: Modifier = Modifier,
    title: String? = null,
    minHeight: Dp? = null,
    selectedDate: LocalDateTime? = null,
    onApply: (LocalDateTime) -> Unit
) {
    var showDatePickerDialog by remember { mutableStateOf(false) }

    val heightModifier = if (minHeight != null) Modifier.heightIn(min = minHeight)
    else Modifier

    val initialMillis = selectedDate?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialMillis
    )

    val dateFormatter = DateTimeFormatter.ofPattern(DateFormats.FULL_DATE, Locale.getDefault())
    val menuText = if (selectedDate != null) {
        selectedDate.format(dateFormatter)
    } else {
        stringResource(R.string.choose_date)
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
                    showDatePickerDialog = !showDatePickerDialog
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
                    ),
                imageVector = ImageVector.vectorResource(R.drawable.icon_calendar),
                contentDescription = "Calendar",
                tint = colors.primaryText
            )
        }
    }

    if (showDatePickerDialog) {
        DatePickerDialog(
            shape = dimen.shape,
            colors = DatePickerDefaults.colors(
                containerColor = Color.White
            ),
            onDismissRequest = { showDatePickerDialog = false },
            confirmButton = {},
            dismissButton = {}
        ) {
            Column {
                DatePicker(
                    modifier = Modifier.weight(1f),
                    state = state,
                    showModeToggle = false,
                    colors = DatePickerDefaults.colors(
                        containerColor = Color.White,
                        selectedDayContentColor = Color.White,
                        selectedDayContainerColor = colors.tintColor,
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
                        state.selectedDateMillis?.let { 
                            val localDateTime = LocalDateTime.ofInstant(
                                Instant.ofEpochMilli(it), 
                                ZoneId.systemDefault()
                            )
                            onApply(localDateTime)
                        }
                    },
                    title = stringResource(R.string.select)
                )

                SecondaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = dimen.paddingXs,
                            horizontal = dimen.containerPadding
                        ),
                    onClick = { showDatePickerDialog = false },
                    title = stringResource(R.string.cancel)
                )
            }
        }
    }
}
