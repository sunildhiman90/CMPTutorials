package com.sunildhiman90.cmplearnings.deeplinks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
@SerialName("deeplink")
data class DeepLinkScreen(
    val id: Int,
    val name: String,
    val profileDesc: String? = null,
    val images: List<String>
)


// Define a home route that doesn't take any arguments
@Serializable
@SerialName("home")
object HomeScreen

@Composable
fun DeepLinkExampleCMP(
    onNavHostReady: suspend (NavController) -> Unit = {}
) {

    val firstBasePath = "app://com.sunildhiman90.cmplearnings"
    val navController = androidx.navigation.compose.rememberNavController()

    NavHost(navController, startDestination = HomeScreen) {
        composable<HomeScreen> {
            Column(
                modifier = androidx.compose.ui.Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "CMP Deep Link Example")
                Spacer(modifier = Modifier.height(16.dp))


            }

        }
        composable<DeepLinkScreen>(
        ) { backStackEntry ->

            //For using the uri with serialname in the route
            val screen: DeepLinkScreen = backStackEntry.toRoute()
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
            ) {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(text = "Go Back")
                }

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "DeepLink with Serialname path Received Data\n Name: ${screen.name}\n Id: ${screen.id}\n ProfileDesc: ${screen.profileDesc}\n Images: ${screen.images}"
                )
            }


            //Alternatively, we can use the uri with parameters in the route

        }
    }

    LaunchedEffect(navController) {
        onNavHostReady(navController)
    }

}