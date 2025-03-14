package ua.wied.presentation.screens.main.composables

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import ua.wied.R
import ua.wied.presentation.common.navigation.BottomBarScreens
import ua.wied.presentation.common.theme.WiEDTheme.colors
import ua.wied.presentation.common.theme.WiEDTheme.typography

@Composable
fun MainBottomAppBar(
    navController: NavHostController
) {
    val screens = BottomBarScreens.entries

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    NavigationBar(
        modifier = Modifier.shadow(10.dp),
        containerColor = colors.primaryBackground,
    ) {
        screens.forEach { screen ->
            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors().copy(
                    selectedTextColor = colors.tintColor,
                    selectedIconColor = colors.tintColor,
                    selectedIndicatorColor = Color.Transparent,

                    unselectedIconColor = colors.primaryText,
                    unselectedTextColor = colors.primaryText
                ),
                selected = currentBackStackEntry?.destination?.hierarchy?.any { it.route == screen.route } == true,
                icon = {
                    Icon(
                        painter = painterResource(screen.icon),
                        contentDescription = stringResource(R.string.instructions)
                    )
                },
                label = {
                    Text(
                        text = screen.label,
                        style = typography.w400.copy(
                            fontSize = 12.sp
                        )
                    )
                },
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            launchSingleTop = true
                            restoreState = true
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                        }
                    }
                }
            )
        }
    }
}