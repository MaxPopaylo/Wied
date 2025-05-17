package ua.wied.presentation.screens.main.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ua.wied.R
import ua.wied.presentation.common.navigation.AccessNav
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.PeopleNav
import ua.wied.presentation.common.navigation.ProfileNav
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography
import ua.wied.presentation.screens.main.models.MainEvent
import ua.wied.presentation.screens.main.models.MainState

@Composable
fun MainTopAppBar(
    isManager: Boolean,
    mainState: MainState,
    navController: NavHostController,
    onEvent: (MainEvent) -> Unit = {}
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val route = navBackStackEntry?.destination?.route

    val profileAction = listOf(
        TopAppBarAction(
            icon = ImageVector.vectorResource(R.drawable.icon_account),
            contentPadding = PaddingValues(2.dp),
            onClick = {
                navController.navigate(ProfileNav.Profile)
            })
    )

    when {
        // Instruction top bars
        route.isRoute(InstructionNav.Instructions::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.instructions),
                actions = (if (isManager) listOf(
                    TopAppBarAction(ImageVector.vectorResource(R.drawable.icon_ai), onClick = {})
                ) else emptyList()) + profileAction
            )
        }

        route.isRoute(InstructionNav.InstructionDetail::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.instruction),
                navController = navController,
                actions = editActions(
                    isManager = isManager,
                    state = mainState.isInstructionEditing,
                    onClick = {
                        onEvent(MainEvent.InstructionEditingChanged(it))
                    }
                ),
                showBack = true
            )
        }

        route.isRoute(InstructionNav.InstructionElementDetail::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.instruction_item),
                navController = navController,
                actions = editActions(
                    isManager = isManager,
                    state = mainState.isElementEditing,
                    onClick = {
                        onEvent(MainEvent.ElementEditingChanged(it))
                    }
                ),
                showBack = true
            )
        }

        route.isRoute(InstructionNav.CreateInstruction::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.create_instruction),
                navController = navController,
                showBack = true
            )
        }

        route.isRoute(InstructionNav.Video::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.all_videos),
                showBack = true,
                onBack = {
                    navController.navigate(InstructionNav.Instructions) {
                        launchSingleTop = true
                        restoreState = false
                        popUpTo(navController.graph.id) { saveState = false }
                    }
                }
            )
        }

        // Report top bars
        route.isRoute(ReportNav.Reports::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.reports),
                actions = profileAction
            )
        }

        route.isRoute(ReportNav.CreateReport::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.create_report),
                navController = navController,
                showBack = true
            )
        }

        route.isRoute(ReportNav.ReportStatusList::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.current_reports),
                navController = navController,
                showBack = true
            )
        }

        route.isRoute(ReportNav.ReportsByStatusList::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(titleForReportsByStatusList(navBackStackEntry)),
                navController = navController,
                showBack = true
            )
        }

        route.isRoute(ReportNav.ReportDetail::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.report),
                navController = navController,
                showBack = true
            )
        }

        // Profile top bar
        route.isRoute(ProfileNav.Profile::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.profile),
                navController = navController,
                showBack = true
            )
        }

        // People top bars
        route.isRoute(PeopleNav.People::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.people),
                actions = profileAction
            )
        }

        route.isRoute(PeopleNav.CreateEmployee::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.create_employee),
                navController = navController,
                showBack = true
            )
        }

        route.isRoute(PeopleNav.EmployeeDetail::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.employee),
                navController = navController,
                showBack = true
            )
        }

        // Access top bars
        route.isRoute(AccessNav.Accesses::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.accesses),
                actions = profileAction
            )
        }

        route.isRoute(AccessNav.FolderDetail::class.qualifiedName) -> {
            RenderTopBar(
                title = stringResource(R.string.folder),
                actions = editActions(
                    isManager = isManager,
                    state = mainState.isAccessEditing,
                    onClick = {
                        onEvent(MainEvent.ElementEditingChanged(it))
                    }
                ),
                navController = navController,
                showBack = true
            )
        }


        else -> {
            RenderTopBar(title = stringResource(R.string.main))
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RenderTopBar(
    title: String,
    navController: NavHostController? = null,
    actions: List<TopAppBarAction> = emptyList(),
    showBack: Boolean = false,
    onBack: (() -> Unit)? = null
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(containerColor = colors.primaryBackground),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimen.topBarPadding),
                text = title,
                style = typography.h3
            )
        },
        navigationIcon = {
            if (showBack) {
                TextButton(
                    modifier = Modifier.padding(start = dimen.padding2Xl).size(dimen.sizeL),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.secondaryBackground,
                        contentColor = colors.primaryText
                    ),
                    shape = dimen.shape,
                    contentPadding = PaddingValues(dimen.paddingM),
                    onClick = { onBack?.invoke() ?: navController?.popBackStack() }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
                        tint = colors.primaryText,
                        contentDescription = stringResource(R.string.icon)
                    )
                }
            }
        },
        actions = {
            Row(
                modifier = Modifier.padding(end = dimen.topBarPadding * 3),
                horizontalArrangement = Arrangement.spacedBy(dimen.paddingS),
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions.forEach { action ->
                    IconButton(
                        modifier = Modifier.size(dimen.sizeM + 2.dp),
                        onClick = action.onClick
                    ) {
                        Icon(
                            modifier = Modifier
                                .size(dimen.sizeM)
                                .padding(action.contentPadding),
                            imageVector = action.icon,
                            tint = action.iconColor ?: colors.tintColor,
                            contentDescription = "Action icon"
                        )
                    }
                }
            }
        }
    )
}

private data class TopAppBarAction(
    val icon: ImageVector,
    val onClick: () -> Unit,
    val contentPadding: PaddingValues = PaddingValues(0.dp),
    val iconColor: Color? = null
)

private fun titleForReportsByStatusList(navBackStackEntry: NavBackStackEntry?) =
    when (navBackStackEntry?.arguments?.getString("status")) {
        "TODO" -> R.string.new_reports
        "IN_PROGRESS" -> R.string.in_progress_reports
        "DONE" -> R.string.done_reports
        else -> R.string.reports
    }

private fun String?.isRoute(destination: String?) =
    this?.startsWith(destination ?: "") == true

@Composable
private fun editActions(isManager: Boolean, state: Boolean?, onClick: (Boolean) -> Unit) = when {
    isManager -> {
        listOf(
            TopAppBarAction(
                ImageVector.vectorResource(
                    if (state == true) R.drawable.icon_save_changes
                    else R.drawable.icon_pencil
                ),
                contentPadding = PaddingValues(
                    if (state == true) dimen.padding2Xs
                    else dimen.zero
                ),
                onClick = {
                    onClick(state?.not() != false)
                }
            )
        )
    }

    else -> emptyList()
}
