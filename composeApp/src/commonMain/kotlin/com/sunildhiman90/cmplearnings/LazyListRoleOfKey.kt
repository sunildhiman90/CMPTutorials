package com.sunildhiman90.cmplearnings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Suppress("ktlint:standard:function-naming")
@Composable
fun LazyColumnListWithoutKeys() {
    var fruits by remember {
        mutableStateOf(
            listOf("Apple", "Banana", "Grapes"),
        )
    }

    LazyColumn(
        modifier = Modifier.padding(top = 32.dp),
    ) {
        items(fruits) { item ->
            var count by remember { mutableStateOf(0) }
            Row(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "$item: $count")
                Button(onClick = {
                    count++
                }) {
                    Text("+")
                }

                Button(onClick = {
                    fruits = fruits.toMutableList().also { it.remove(item) }
                }) {
                    Text("Delete")
                }
            }
        }
    }
}
