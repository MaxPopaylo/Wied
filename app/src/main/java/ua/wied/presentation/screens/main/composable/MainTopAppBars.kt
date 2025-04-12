package ua.wied.presentation.screens.main.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ua.wied.R
import ua.wied.presentation.common.composable.IconButton
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.dimen
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun MainTopAppBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinationRoute = navBackStackEntry?.destination?.route

    when {
        currentDestinationRoute == InstructionNav.Instructions::class.qualifiedName ->
            DefaultTopAppBar(stringResource(R.string.instructions))

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
                    .padding(start = 4.5.dp),
                text = title,
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 24.sp
                )
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarWithCloseButton(
    title: String,
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
                    .padding(start = 7.5.dp),
                text = title,
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 24.sp
                )
            )
        },
        actions = {
            IconButton(
                modifier = Modifier.size(dimen.sizeM),
                icon = painterResource(R.drawable.icon_close),
                iconColor = colors.primaryText,
                backgroundColor = Color.Transparent,
                borderColor = Color.Transparent,
                onClick = {
                    navController.popBackStack()
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarWithBackButton(
    title: String,
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
                    .padding(start = 4.5.dp),
                text = title,
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 24.sp
                )
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
                onClick = {
                    navController.popBackStack()
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_arrow_back),
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
