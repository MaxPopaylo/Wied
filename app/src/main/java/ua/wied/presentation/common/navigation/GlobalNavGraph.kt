package ua.wied.presentation.common.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.coroutines.flow.StateFlow
import ua.wied.presentation.screens.auth.AuthScreen
import ua.wied.presentation.screens.main.MainScreen
import kotlin.reflect.KType


@Composable
fun GlobalNavGraph(
    navController: NavHostController,
    startDestination: GlobalNav
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

//        composable<GlobalNav.Auth>(
//            enterTransition = {
//                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
//            },
//            exitTransition = {
//                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
//            },
//            popEnterTransition = {
//                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
//            },
//            popExitTransition = {
//                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
//            }
//        ) {
//            AuthScreen(globalNavController = navController)
//        }

        screenComposable<GlobalNav.Auth>(TabType.GLOBAL) {
            AuthScreen(globalNavController = navController)
        }

//        composable<GlobalNav.Main>(
//            enterTransition = {
//                slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
//            },
//            exitTransition = {
//                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
//            },
//            popEnterTransition = {
//                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
//            },
//            popExitTransition = {
//                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
//            }
//        ) { backStackEntry ->
//            val args = backStackEntry.toRoute<GlobalNav.Main>()
//            MainScreen(isManager = args.isManager)
//        }

        screenComposable<GlobalNav.Main>(TabType.GLOBAL) { backStackEntry ->
            val args = backStackEntry.toRoute<GlobalNav.Main>()
            MainScreen(isManager = args.isManager)
        }

    }
}

inline fun <reified Destination : Any> NavGraphBuilder.screenComposable(
    tabType: TabType = TabType.DEFAULT,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline content: @Composable (entry: NavBackStackEntry) -> Unit
) {
    composable<Destination>(
        typeMap = typeMap,
        enterTransition = { tabType.enterTransition() },
        exitTransition = { tabType.exitTransition() },
        popEnterTransition = { tabType.popEnterTransition() },
        popExitTransition = { tabType.popExitTransition() }
    ) { entry ->
        content(entry)
    }
}

inline fun <
  reified Destination : Any,
  reified VM : ViewModel,
  S
> NavGraphBuilder.screenComposable(
    tabType: TabType = TabType.DEFAULT,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline stateProvider: (viewModel: VM) -> StateFlow<S>,
    noinline content: @Composable (viewModel: VM, state: S, entry: NavBackStackEntry) -> Unit
) {
    composable<Destination>(
        typeMap = typeMap,
        enterTransition = { tabType.enterTransition() },
        exitTransition = { tabType.exitTransition() },
        popEnterTransition = { tabType.popEnterTransition() },
        popExitTransition = { tabType.popExitTransition() }
    ) { entry ->
        val viewModel: VM = hiltViewModel(entry)
        val state by stateProvider(viewModel).collectAsState()
        content(viewModel, state, entry)
    }
}

enum class TabType {
    DEFAULT, START, BACK, GLOBAL;
}

fun TabType.enterTransition() = when (this) {
    TabType.START -> fadeIn(tween(500))
    TabType.BACK -> slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
    TabType.GLOBAL -> slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(500))
    else -> null
}

fun TabType.exitTransition() = when (this) {
    TabType.START -> fadeOut(tween(300))
    TabType.BACK -> fadeOut(tween(300))
    TabType.GLOBAL -> slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tween(500))
    else -> null
}

fun TabType.popEnterTransition() = when (this) {
    TabType.START -> slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
    TabType.BACK -> slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
    TabType.GLOBAL -> slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(500))
    else -> null
}

fun TabType.popExitTransition() = when (this) {
    TabType.START -> fadeOut(tween(300))
    TabType.BACK -> fadeOut(tween(300))
    TabType.GLOBAL -> slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(500))
    else -> null
}