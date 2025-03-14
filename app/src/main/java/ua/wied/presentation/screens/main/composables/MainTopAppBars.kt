package ua.wied.presentation.screens.main.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ua.wied.R
import ua.wied.presentation.common.composable.IconButton
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun MainTopAppBar(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestinationRoute = navBackStackEntry?.destination?.route

    when(currentDestinationRoute) {
        InstructionNav.Instructions.route -> DefaultTopAppBar(stringResource(R.string.instructions))
        ReportNav.Reports.route -> DefaultTopAppBar(stringResource(R.string.reports))

        else -> DefaultTopAppBar(stringResource(R.string.main))
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
                    .padding(16.dp),
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
                    .padding(16.dp),
                text = title,
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 24.sp
                )
            )
        },
        actions = {
            IconButton(
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
private fun TopAppBarWithBackground(
    title: String,
    navController: NavHostController
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors().copy(
            containerColor = colors.secondaryBackground
        ),
        title = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = title,
                color = colors.primaryText,
                style = typography.w500.copy(
                    fontSize = 24.sp
                )
            )
        },
        navigationIcon = {
            IconButton(
                icon = painterResource(R.drawable.icon_arrow_back),
                iconColor = colors.primaryText,
                backgroundColor = colors.primaryBackground,
                borderColor = colors.primaryBackground,
                onClick = {
                    navController.popBackStack()
                }
            )
        }
    )
}