package ua.wied.presentation.common.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ua.wied.presentation.common.base.BaseNetworkResponseState
import ua.wied.presentation.common.theme.WiEDTheme.colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentBox(
    modifier: Modifier = Modifier,
    state: BaseNetworkResponseState,
    onRefresh: (() -> Unit)? = null,
    emptyScreen: (@Composable () -> Unit)? = null,
    loadingContent: @Composable (() -> Unit)? = null,
    mainContent: @Composable () -> Unit
) {
    var isRefreshingWorkaround by remember { mutableStateOf(state.isRefreshing) }
    LaunchedEffect(key1 = state.isRefreshing) {
        isRefreshingWorkaround = state.isRefreshing
    }
    val pullRefreshState = rememberPullToRefreshState()


    Box(
        modifier = modifier
            .fillMaxSize()
            .pullToRefresh(
                state = pullRefreshState,
                isRefreshing = isRefreshingWorkaround,
                onRefresh = { onRefresh?.invoke() }
            )
    ) {
        when {
            state.isLoading -> {
                if (loadingContent != null) {
                    loadingContent()
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = colors.tintColor
                    )
                }
            }

            state.isNotInternetConnection -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center
                ) {
                    NoConnectionScreen()
                }
            }

            state.isEmpty && emptyScreen != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Center
                ) {
                    emptyScreen.invoke()
                }
            }

            else -> mainContent()
        }

        PullToRefreshDefaults.Indicator(
            state = pullRefreshState,
            isRefreshing = isRefreshingWorkaround,
            containerColor = colors.primaryBackground,
            color = colors.tintColor,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
