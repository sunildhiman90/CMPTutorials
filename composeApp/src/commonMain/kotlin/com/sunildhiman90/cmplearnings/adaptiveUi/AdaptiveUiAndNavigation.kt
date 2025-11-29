package com.sunildhiman90.cmplearnings.adaptiveUi

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.layout.AnimatedPaneOverrideScope
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.SupportingPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import cmplearnings.composeapp.generated.resources.Res
import cmplearnings.composeapp.generated.resources.cart
import cmplearnings.composeapp.generated.resources.home
import cmplearnings.composeapp.generated.resources.profile
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun AdaptiveUiAndNavigation() {

    var currentDestination by remember {
        mutableStateOf(AppDestinations.HOME)
    }

    val adaptiveInfo = currentWindowAdaptiveInfo()

    val layoutType =
        if (adaptiveInfo.windowSizeClass.isWidthAtLeastBreakpoint(WindowSizeClass.WIDTH_DP_EXPANDED_LOWER_BOUND)) {
            NavigationSuiteType.NavigationDrawer
        } else {
            NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(adaptiveInfo)
        }

    NavigationSuiteScaffold(
        layoutType = layoutType,
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    selected = currentDestination == it,
                    onClick = {
                        currentDestination = it
                    },
                    icon = {
                        Icon(
                            imageVector = it.icon,
                            contentDescription = stringResource(it.contentDescription)
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(it.label)
                        )
                    },
                    alwaysShowLabel = true
                )
            }
        },
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            navigationDrawerContainerColor = MaterialTheme.colorScheme.surfaceContainer,
            navigationDrawerContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            navigationRailContainerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        when (currentDestination) {
            AppDestinations.HOME -> HomeDestination()
            AppDestinations.CART -> CartDestination()
            AppDestinations.PROFILE -> ProfileDestination()
        }
    }

}

@Composable
fun Destination(content: @Composable () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}


@Composable
fun HomeDestination() {
    Destination {
        ListDetailPaneScaffoldContent()
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun ListDetailPaneScaffoldContent() {

    val navigator = rememberListDetailPaneScaffoldNavigator<ListDataItem>()

    val scope = rememberCoroutineScope()

    ListDetailPaneScaffold(
        modifier = Modifier.statusBarsPadding(),
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            Box(
                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary)
            ) {

                LazyColumn {
                    items(testData) { item ->
                        ListItem(
                            modifier = Modifier.clickable {
                                scope.launch {
                                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, item)
                                }
                            },
                            headlineContent = {
                                Text(item.title)
                            })
                    }
                }

            }
        },
        detailPane = {
            navigator.currentDestination?.contentKey?.let {
                val text = it.title

                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .clickable {
                            scope.launch {
                                navigator.navigateTo(ListDetailPaneScaffoldRole.Extra, it)
                            }
                        }
                ) {

                    IconButton(onClick = {
                        scope.launch {
                            navigator.navigateBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Detail: ${it.title}", modifier = Modifier.padding(horizontal = 16.dp))

                }
            }
        },
        extraPane = {
            navigator.currentDestination?.contentKey?.let {
                val text = it.title

                Column(
                    modifier = Modifier.fillMaxSize()
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                ) {

                    IconButton(onClick = {
                        scope.launch {
                            navigator.navigateBack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Extra Pane Title: ${it.title}", modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    )
}

@Composable
fun CartDestination() {
    Destination {
        Text("Cart")
    }
}

@Composable
fun ProfileDestination() {
    Destination {
        Text("Profile")
    }
}

enum class AppDestinations(
    val label: StringResource,
    val icon: ImageVector,
    val contentDescription: StringResource
) {
    HOME(Res.string.home, Icons.Default.Home, Res.string.home),
    CART(Res.string.cart, Icons.Default.ShoppingCart, Res.string.cart),
    PROFILE(Res.string.profile, Icons.Default.AccountBox, Res.string.profile),
}


val testData = listOf<ListDataItem>(
    ListDataItem("Item 1"),
    ListDataItem("Item 2"),
    ListDataItem("Item 3"),
    ListDataItem("Item 4"),
    ListDataItem("Item 5"),
    ListDataItem("Item 6"),
    ListDataItem("Item 7"),
    ListDataItem("Item 8"),
    ListDataItem("Item 9"),
    ListDataItem("Item 10"),
    ListDataItem("Item 11"),
    ListDataItem("Item 12"),
    ListDataItem("Item 13"),
    ListDataItem("Item 14"),
    ListDataItem("Item 15"),
    ListDataItem("Item 16"),
    ListDataItem("Item 17"),
    ListDataItem("Item 18"),
    ListDataItem("Item 19"),
    ListDataItem("Item 20"),
    ListDataItem("Item 21"),
    ListDataItem("Item 22"),
)

data class ListDataItem(val title: String)
