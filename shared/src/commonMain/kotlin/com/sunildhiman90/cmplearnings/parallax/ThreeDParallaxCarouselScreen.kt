package com.sunildhiman90.cmplearnings.parallax
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cmplearnings.shared.generated.resources.Res
import cmplearnings.shared.generated.resources.poster1
import cmplearnings.shared.generated.resources.poster2
import cmplearnings.shared.generated.resources.poster3

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs

@Composable
fun ThreeDParallaxCarouselScreen() {
    val posters = listOf(
        MoviePoster("Neon Horizon", Res.drawable.poster1, "Cyberpunk Sci-Fi"),
        MoviePoster("The Forgotten Kingdom", Res.drawable.poster2, "Epic Fantasy"),
        MoviePoster("Beyond Void", Res.drawable.poster3, "Space Exploration"),
        // Reuse for 5 cards demo
        MoviePoster("Cyber City 2077", Res.drawable.poster1, "Action RPG"),
        MoviePoster("Ancient Legends", Res.drawable.poster2, "Adventure")
    )

    val pagerState = rememberPagerState(pageCount = { posters.size })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "3d Parallax Carousel - Cinematic Universe",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 32.dp)
            )

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 48.dp),
                pageSpacing = 16.dp
            ) { page ->
                ParallaxCard(
                    poster = posters[page],
                    page = page,
                    pagerState = pagerState
                )
            }
        }
    }
}

/**
 * The core logic highlighted in the Reel
 */
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}

@Composable
fun ParallaxCard(
    poster: MoviePoster,
    page: Int,
    pagerState: PagerState
) {
    val pageOffset = pagerState.calculateCurrentOffsetForPage(page)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .graphicsLayer {

                val pageScale = 1f - abs(pageOffset) * 0.1f
                scaleX = pageScale
                scaleY = pageScale

                alpha = 1f - abs(pageOffset) * 0.3f

                rotationY = pageOffset * -15f

                cameraDistance = 8f * density

            }
            .clip(RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Image(
                painter = painterResource(poster.imageRes),
                contentDescription = poster.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {

                        translationX = -pageOffset * 250f

                        scaleX = 1.6f
                        scaleY = 1.6f
                    }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .height(120.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                        )
                    )
                    .padding(16.dp)
            ) {

                Column {

                    Text(
                        text = poster.genre,
                        color = Color.Cyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    Text(
                        text = poster.title,
                        color = Color.Cyan,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

        }

    }

}


data class MoviePoster(
    val title: String,
    val imageRes: DrawableResource,
    val genre: String
)

@Preview
@Composable
fun ThreeDParallaxCarouselPreview() {
    ThreeDParallaxCarouselScreen()
}
