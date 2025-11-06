package com.sunildhiman90.cmplearnings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeViewport
import com.sunildhiman90.cmplearnings.webElementView.IframeMapExample
import com.sunildhiman90.cmplearnings.webElementView.VideoPlayer

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {

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
}

