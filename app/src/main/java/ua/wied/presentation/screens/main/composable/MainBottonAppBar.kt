package ua.wied.presentation.screens.main.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ua.wied.R
import ua.wied.presentation.common.navigation.BottomBarScreen
import ua.wied.presentation.common.navigation.EvaluationNav
import ua.wied.presentation.common.navigation.InstructionNav
import ua.wied.presentation.common.navigation.ProfileNav
import ua.wied.presentation.common.navigation.ReportNav
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun MainBottomAppBar(
    isManager: Boolean,
    navController: NavHostController
) {
    val screens = remember {
        when {
            isManager -> {
                listOf(
                    BottomBarScreen.Instructions,
                    BottomBarScreen.Reports,
                    BottomBarScreen.Evaluations,
                    BottomBarScreen.Accesses,
                    BottomBarScreen.People
                )
            }

            else -> {
                listOf(
                    BottomBarScreen.Instructions,
                    BottomBarScreen.Reports,
                    BottomBarScreen.Evaluations,
                    BottomBarScreen.People
                )
            }
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier.shadow(10.dp),
        containerColor = colors.primaryBackground,
    ) {
        screens.forEach { screen ->
            val isSelected = isSelected(currentDestination, screen)
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedTextColor = colors.tintColor,
                    selectedIconColor = colors.tintColor,
                    selectedIndicatorColor = Color.Transparent,

                    unselectedIconColor = colors.primaryText,
                    unselectedTextColor = colors.primaryText
                ),
                selected = isSelected,
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(screen.icon),
                        contentDescription = stringResource(R.string.instructions)
                    )
                },
                label = {
                    Text(
                        text = stringResource(screen.label),
                        color = if (isSelected) colors.tintColor
                                else colors.primaryText,
                        style = typography.body4
                    )
                },
                onClick = {
                    if (currentDestination != screen) {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = false
                            popUpTo(navController.graph.id) {
                                saveState = false
                            }
                        }
                    }
                }
            )
        }
    }
}

private fun isSelected(currentDestination: NavDestination?, screen: BottomBarScreen): Boolean {
    val currentRoute = currentDestination?.route ?: return false

    val parentDestination = when {
        "InstructionNav" in currentRoute -> InstructionNav.Instructions
        "ReportNav" in currentRoute -> ReportNav.Reports
        "EvaluationNav" in currentRoute -> EvaluationNav.Evaluations
        "ProfileNav" in currentRoute -> ProfileNav.Profile
        else -> null
    }

    return parentDestination == screen.route
}