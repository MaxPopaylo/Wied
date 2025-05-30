package ua.wied.presentation.screens.people.detail

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.wied.R
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.theme.WiEDTheme.dimen

@Composable
fun EmployeeDetail(
    user: User
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .scrollable(scrollState, Orientation.Vertical)
            .padding(top = dimen.containerPaddingLarge),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
    ) {
        DetailTextField(
            title = stringResource(R.string.login),
            text = user.login,
        )

        DetailTextField(
            title = stringResource(R.string.full_name),
            text = user.name,
        )

        user.info?.let {
            DetailTextField(
                title = stringResource(R.string.company),
                text = it,
            )
        }

        DetailTextField(
            title = stringResource(R.string.company),
            text = user.phone,
        )

        DetailTextField(
            title = stringResource(R.string.company),
            text = user.company,
        )

        DetailTextField(
            title = stringResource(R.string.position),
            text = stringResource(user.role.displayName),
        )
    }
}