package ua.wied.presentation.common.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ua.wied.R
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.common.utils.bounceClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeesBottomSheet(
    employees: List<User>,
    onClose: () -> Unit,
    userChosen: (User) -> Unit
) {
    BaseBottomSheet(
        onClose = onClose,
        cancelButtonText = stringResource(R.string.cancel),
        header = {
            Text(
                text = stringResource(R.string.employees),
                style = typography.h4
            )
        }
    ) {
        if (employees.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimen.paddingXs)
                    .height(400.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.size(dimen.sizeL),
                    imageVector = ImageVector.vectorResource(R.drawable.icon_people),
                    tint = colors.primaryText,
                    contentDescription = "People"
                )
                Text(
                    modifier = Modifier.padding(top = dimen.padding2Xs),
                    text = stringResource(R.string.no_employees),
                    style = typography.body1
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimen.paddingXl)
                    .heightIn(max = 400.dp),
                verticalArrangement = Arrangement.spacedBy(dimen.paddingM)
            ) {
                items(employees) { employee ->
                    EmployeeListItem(
                        employee = employee,
                        onClick = {
                            userChosen(employee)
                            onClose()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun EmployeeListItem(
    employee: User,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colors.secondaryBackground,
                shape = dimen.shape
            )
            .bounceClick(onClick)
            .padding(
                vertical = dimen.paddingL,
                horizontal = dimen.paddingL
            ),
        horizontalArrangement = Arrangement.spacedBy(dimen.paddingM),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = employee.name,
            style = typography.button3
        )
    }
}
