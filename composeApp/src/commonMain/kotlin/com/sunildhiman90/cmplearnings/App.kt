package com.sunildhiman90.cmplearnings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.sunildhiman90.cmplearnings.deeplinks.DeepLinkExampleCMP
import com.sunildhiman90.cmplearnings.kotlin_2_2_20.WhatsNewInKotlin_2_2_20
import com.sunildhiman90.cmplearnings.webview.CMPWebViewExample
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {
    MaterialTheme {
        //WhatsNewInKotlin_2_2_20()
        //DeepLinkExampleCMP(onNavHostReady)
        CMPWebViewExample()
    }
}