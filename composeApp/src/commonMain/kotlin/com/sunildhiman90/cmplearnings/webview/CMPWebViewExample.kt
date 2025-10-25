package com.sunildhiman90.cmplearnings.webview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.multiplatform.webview.util.KLogSeverity
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewNavigator
import com.multiplatform.webview.web.rememberWebViewState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMPWebViewExample() {


    val url = "https://flipkart.com"
    val state = rememberWebViewState(url)

    LaunchedEffect(Unit) {
        state.webSettings.apply {
            logSeverity = KLogSeverity.Debug
            customUserAgentString = "Mozilla/5.0"
        }
    }


    val navigator = rememberWebViewNavigator()

    Column {

        TopAppBar(
            title = { Text("CMP WebView Example") },
            navigationIcon = {
                if (navigator.canGoBack) {
                    IconButton(
                        onClick = {
                            navigator.navigateBack()
                        }) {
                        Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = null)
                    }
                }
            }
        )

        val loadingState = state.loadingState

        if (loadingState is LoadingState.Loading) {
            LinearProgressIndicator(progress = {
                loadingState.progress
            }, modifier = Modifier.fillMaxWidth())
        }

        WebView(
            state,
            modifier = Modifier.fillMaxSize(),
            navigator = navigator
        )

    }


}


