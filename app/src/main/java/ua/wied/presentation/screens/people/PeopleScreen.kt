package ua.wied.presentation.screens.people

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import ua.wied.domain.models.user.User
import ua.wied.presentation.common.composable.ContentBox
import ua.wied.presentation.common.composable.ItemList
import ua.wied.presentation.common.composable.SearchField
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.people.composable.EmployeeEmptyScreen
import ua.wied.presentation.screens.people.composable.UserListItem
import ua.wied.presentation.screens.people.model.PeopleEvent
import ua.wied.presentation.screens.people.model.PeopleState

@Composable
fun PeopleScreen(
    state: PeopleState,
    isManager: Boolean,
    savedStateHandle: SavedStateHandle,
    onMainEvent: (MainEvent) -> Unit,
    onEvent: (PeopleEvent) -> Unit,
    navigateToCreation: () -> Unit,
    navigateToDetail: (User) -> Unit
) {
    val shouldRefresh = savedStateHandle
        .getStateFlow("shouldRefresh", false)
        .collectAsState()

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh.value) {
            onEvent(PeopleEvent.Refresh)
            savedStateHandle["shouldRefresh"] = false
        }
    }

    LaunchedEffect(isManager) {
        if (isManager) {
            onMainEvent(MainEvent.FabVisibilityChanged(true))
            onMainEvent(MainEvent.FabClickChanged(value = {
                navigateToCreation()
            }))
        }
    }

    Column(
        modifier = Modifier.padding(bottom = dimen.paddingS),
        verticalArrangement = Arrangement.spacedBy(dimen.padding2Xl)
    ) {
        SearchField(
            text = state.search,
            onSearchValueChange = {
                onEvent(PeopleEvent.SearchChanged(it))
            }
        )
        ContentBox(
            state = state,
            onRefresh = { onEvent(PeopleEvent.Refresh) },
            emptyScreen = { EmployeeEmptyScreen(navigateToCreation) }
        ) {
            ItemList (
                items = state.employees,
                itemView = { user ->
                    UserListItem(
                        user = user,
                        isManager = isManager,
                        onClick = {
                            navigateToDetail(user)
                        },
                        onDelete = {
                            onEvent(PeopleEvent.DeletePressed(it))
                        }
                    )
                }
            )
        }
    }
}