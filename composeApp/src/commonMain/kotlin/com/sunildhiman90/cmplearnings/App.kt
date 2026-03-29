package com.sunildhiman90.cmplearnings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sunildhiman90.cmplearnings.adaptiveUi.AdaptiveUiAndNavigation
import com.sunildhiman90.cmplearnings.deeplinks.DeepLinkExampleCMP
import com.sunildhiman90.cmplearnings.kotlin_2_2_20.WhatsNewInKotlin_2_2_20
import com.sunildhiman90.cmplearnings.webview.CMPWebViewExample
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App(onNavHostReady: suspend (NavController) -> Unit = {}) {
    MaterialTheme {
        Scaffold(
            modifier = Modifier.statusBarsPadding(),
            topBar = {
                TopAppBar(
                    title = { Text("Role Of Key In LazyLists") },
                )
            },
        ) {
            Column(modifier = Modifier.padding(it).padding(horizontal = 16.dp)) {
                LazyColumnListWithoutKeys()
            }
        }
    }
}
