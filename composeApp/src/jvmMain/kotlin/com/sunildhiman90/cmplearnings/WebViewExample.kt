package com.sunildhiman90.cmplearnings


import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.datlag.kcef.KCEF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.max



@Composable
fun WebViewExample() {
    var restartRequired by remember { mutableStateOf(false) }
    var downloading by remember { mutableStateOf(0F) }
    var initialized by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            KCEF.init(builder = {
                installDir(File("kcef-bundle"))
                progress {
                    onDownloading {
                        downloading = max(it, 0F)
                    }
                    onInitialized {
                        initialized = true
                    }
                }
                settings {
                    cachePath = File("cache").absolutePath
                }
            }, onError = {
                it?.printStackTrace()
            }, onRestartRequired = {
                restartRequired = true
            })
        }
    }

    if (restartRequired) {
        Text(text = "Restart required.")
    } else {
        if (initialized) {
            App()
        } else {
            Text(text = "Downloading $downloading%")
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            KCEF.disposeBlocking()
        }
    }
}