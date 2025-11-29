

package com.sunildhiman90.cmplearnings.webElementView

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.WebElementView
import kotlinx.browser.document
import org.w3c.dom.HTMLIFrameElement
import org.w3c.dom.HTMLVideoElement

@Composable
fun WebHtmlElementViewExample() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column {
            Spacer(modifier = Modifier.height(16.dp))
            Text("Using Html Element in Compose Web", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.height(350.dp).width(350.dp)) {
                IframeMapExample()
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.height(350.dp).width(350.dp)) {
                VideoPlayer()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
@Composable
fun VideoPlayer() {
    Box(modifier = Modifier.fillMaxSize()) {

        WebElementView(
            factory = {

                val videoElement = (document.createElement("video") as HTMLVideoElement)
                videoElement.src = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                videoElement.controls = true
                videoElement.muted = true
                videoElement.play()
                videoElement
            },
            modifier = Modifier.fillMaxSize(),
            update = { videoElement ->
                videoElement.src = videoElement.src
            },
            onRelease = {

            }
        )

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun IframeMapExample() {
    val url = "https://www.openstreetmap.org/export/embed.html?bbox=45.615234375%2C8.754794702435618%2C101.07421875%2C37.43997405227057&amp;layer=mapnik"

    Box(modifier = Modifier.fillMaxSize()) {

        WebElementView(
            factory = {

                val iframeElement = (document.createElement("iframe") as HTMLIFrameElement)
                iframeElement.src = url
                iframeElement

            },
            modifier = Modifier.fillMaxSize(),
            update = { element ->
                element.src = url
            },
            onRelease = {

            }
        )

    }
}