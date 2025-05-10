package ua.wied.presentation.screens.profile

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.wied.R
import ua.wied.domain.models.settings.Language
import ua.wied.domain.models.settings.Theme
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.DetailTextField
import ua.wied.presentation.common.composable.TypeDropdown
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.profile.model.ProfileEvent
import ua.wied.presentation.screens.profile.model.ProfileState

@Composable
fun ProfileScreen(
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    ContentBox(
        state = state,
        onRefresh = {}
    ) {
        Column(
            modifier = Modifier
                .scrollable(scrollState, Orientation.Vertical)
                .padding(top = dimen.containerPaddingLarge),
            verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
        ) {
            DetailTextField(
                title = stringResource(R.string.full_name),
                text = state.user?.name ?: "...",
            )

            DetailTextField(
                title = stringResource(R.string.company),
                text = state.user?.company ?: "...",
            )

            DetailTextField(
                title = stringResource(R.string.login),
                text = state.user?.login ?: "...",
            )

            DetailTextField(
                title = stringResource(R.string.position),
                text = state.user?.role?.displayName ?: "...",
            )

            TypeDropdown(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.language),
                types = Language.entries.toList(),
                initType = state.settings.language,
                onSelected = {
                    onEvent(ProfileEvent.ChangeLanguage(it))
                }
            )

            TypeDropdown(
                modifier = Modifier.fillMaxWidth(),
                title = stringResource(R.string.theme),
                types = Theme.entries.toList(),
                iconColor = colors.primaryText,
                initType = if (state.settings.darkTheme == true) Theme.DARK
                           else Theme.LIGHT,
                onSelected = {
                    onEvent(ProfileEvent.ChangeTheme(it))
                }
            )
        }
    }
}