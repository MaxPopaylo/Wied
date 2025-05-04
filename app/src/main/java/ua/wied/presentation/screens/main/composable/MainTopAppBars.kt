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
import ua.wied.presentation.common.navigation.InstructionNav
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
    val currentDestinationRoute = navBackStackEntry?.destination?.route

    val instructionListActions = when {
        isManager -> {
            listOf(
                TopAppBarAction(ImageVector.vectorResource(R.drawable.icon_ai), onClick = {}),
            )
        }

        else -> {
            emptyList()
        }
    }

    val instructionDetailActions = when {
        isManager -> {
            listOf(
                TopAppBarAction(
                    ImageVector.vectorResource(
                        if (mainState.isInstructionEditing == true) R.drawable.icon_save_changes
                        else R.drawable.icon_pencil
                    ),
                    contentPadding = PaddingValues(
                        if (mainState.isInstructionEditing == true) dimen.padding2Xs
                        else dimen.zero
                    ),
                    onClick = {
                        onEvent(MainEvent.InstructionEditingChanged(mainState.isInstructionEditing?.not() != false))
                    }
                )
            )
        }

        else -> emptyList()
    }

    val elementDetailActions = when {
        isManager -> {
            listOf(
                TopAppBarAction(
                    ImageVector.vectorResource(
                        if (mainState.isInstructionEditing == true) R.drawable.icon_save_changes
                        else R.drawable.icon_pencil
                    ),
                    contentPadding = PaddingValues(
                        if (mainState.isInstructionEditing == true) dimen.padding2Xs
                        else dimen.zero
                    ),
                    onClick = {
                        onEvent(MainEvent.InstructionEditingChanged(mainState.isInstructionEditing?.not() != false))
                    }
                )
            )
        }

        else -> emptyList()
    }

    when {
        currentDestinationRoute == InstructionNav.Instructions::class.qualifiedName ->
            TopAppBarWithActions(stringResource(R.string.instructions), instructionListActions)

        currentDestinationRoute?.startsWith(InstructionNav.InstructionDetail::class.qualifiedName ?: "") == true ->
            TopAppBarWithBackAndActions(stringResource(R.string.instruction), instructionDetailActions, navController)

        currentDestinationRoute?.startsWith(InstructionNav.InstructionElementDetail::class.qualifiedName ?: "") == true ->
            TopAppBarWithBackAndActions(stringResource(R.string.video), elementDetailActions, navController)

        currentDestinationRoute?.startsWith(InstructionNav.CreateInstruction::class.qualifiedName ?: "") == true ->
            TopAppBarWithBackButton(stringResource(R.string.create_instruction), navController)

        currentDestinationRoute?.startsWith(InstructionNav.Video::class.qualifiedName ?: "") == true ->
            TopAppBarWithBackButton(
                stringResource(R.string.all_videos),
                navController,
                onBack = {
                    navController.navigate(InstructionNav.Instructions) {
                        launchSingleTop = true
                        restoreState = false
                        popUpTo(navController.graph.id) {
                            saveState = false
                        }
                    }
                }
            )



        currentDestinationRoute == ReportNav.Reports::class.qualifiedName ->
            DefaultTopAppBar(stringResource(R.string.reports))

        currentDestinationRoute?.startsWith(ReportNav.CreateReport::class.qualifiedName ?: "") == true ->
            TopAppBarWithBackButton(stringResource(R.string.create_report), navController)

        currentDestinationRoute?.startsWith(ReportNav.ReportStatusList::class.qualifiedName ?: "") == true ->
            TopAppBarWithBackButton(stringResource(R.string.current_reports), navController)

        currentDestinationRoute?.startsWith(ReportNav.ReportsByStatusList::class.qualifiedName ?: "") == true ->
            TopAppBarWithBackButton(stringResource(titleForReportsByStatusList(navBackStackEntry)), navController)

        currentDestinationRoute?.startsWith(ReportNav.ReportDetail::class.qualifiedName ?: "") == true ->
            TopAppBarWithBackButton(stringResource(R.string.report), navController)



        else ->
            DefaultTopAppBar(stringResource(R.string.main))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DefaultTopAppBar(
    title: String
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = colors.primaryBackground
        ),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimen.topBarPadding),
                text = title,
                style = typography.h3
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarWithBackAndActions(
    title: String,
    actions: List<TopAppBarAction>,
    navController: NavHostController
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = colors.primaryBackground
        ),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimen.topBarPadding),
                text = title,
                style = typography.h3
            )
        },
        actions = {
            Row(
                modifier = Modifier
                    .padding(end = (dimen.topBarPadding * 3)),
                horizontalArrangement = Arrangement.spacedBy(dimen.paddingS),
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions.forEach { action ->
                    IconButton(
                        modifier = Modifier
                            .size(dimen.sizeM + 2.dp),
                        onClick = action.onClick,
                        content = {
                            Icon(
                                modifier = Modifier
                                    .size(dimen.sizeM)
                                    .padding(action.contentPadding),
                                imageVector = action.icon,
                                tint = colors.tintColor,
                                contentDescription = "Action icon"
                            )
                        }
                    )
                }
            }
        },
        navigationIcon = {
            TextButton(
                modifier = Modifier.padding(start = dimen.padding2Xl).size(dimen.sizeL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.secondaryBackground,
                    contentColor = colors.primaryText
                ),
                shape = dimen.shape,
                contentPadding = PaddingValues(dimen.paddingM),
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
                    tint = colors.primaryText,
                    contentDescription = stringResource(R.string.icon)
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarWithActions(
    title: String,
    actions: List<TopAppBarAction>
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = colors.primaryBackground
        ),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = dimen.topBarPadding),
                text = title,
                style = typography.h3
            )
        },
        actions = {
            Row(
                modifier = Modifier
                    .padding(end = (dimen.topBarPadding * 3)),
                horizontalArrangement = Arrangement.spacedBy(dimen.paddingS),
                verticalAlignment = Alignment.CenterVertically
            ) {
                actions.forEach { action ->
                    IconButton(
                        modifier = Modifier
                            .size(dimen.sizeM + 2.dp),
                        onClick = action.onClick,
                        content = {
                            Icon(
                                modifier = Modifier
                                    .size(dimen.sizeM)
                                    .padding(action.contentPadding),
                                imageVector = action.icon,
                                tint = action.iconColor ?: colors.tintColor,
                                contentDescription = "Action icon"
                            )
                        }
                    )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarWithBackButton(
    title: String,
    navController: NavHostController,
    onBack: () -> Unit = { navController.popBackStack() }
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = colors.primaryBackground
        ),
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
            TextButton(
                modifier = Modifier.padding(start = dimen.padding2Xl).size(dimen.sizeL),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.secondaryBackground,
                    contentColor = colors.primaryText
                ),
                shape = dimen.shape,
                contentPadding = PaddingValues(dimen.paddingM),
                onClick = onBack,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.icon_arrow_back),
                    tint = colors.primaryText,
                    contentDescription = stringResource(R.string.icon)
                )
            }
        }
    )
}


private fun titleForReportsByStatusList(navBackStackEntry: NavBackStackEntry?) =
    when (navBackStackEntry?.arguments?.getString("status")) {
        "TODO" -> R.string.new_reports
        "IN_PROGRESS" -> R.string.in_progress_reports
        "DONE" -> R.string.done_reports
        else -> R.string.reports
    }
