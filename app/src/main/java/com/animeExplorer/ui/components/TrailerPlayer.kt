package com.animeExplorer.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.animeExplorer.util.AppConstants

/**
 * Composable for playing YouTube trailers or showing poster images with fallback.
 * Handles YouTube videos and provides poster image fallback when no trailer is available.
 * 
 * @param youtubeId YouTube video ID for the trailer, null if no trailer available
 * @param posterImageUrl URL of the poster image to display as fallback when no trailer is available
 * @param modifier Optional modifier to apply to the component
 * @author udit
 */
@Composable
fun TrailerPlayer(
    youtubeId: String?,
    posterImageUrl: String?,
    modifier: Modifier = Modifier
) {
    when {
        // Play YouTube video
        !youtubeId.isNullOrBlank() -> {
            YouTubePlayer(
                youtubeId = youtubeId,
                modifier = modifier
            )
        }
        // No trailer available, show poster image
        else -> {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(AppConstants.VIDEO_PLAYER_HEIGHT.dp)
                    .background(Color.Black)
            ) {
                AsyncImage(
                    model = posterImageUrl,
                    contentDescription = AppConstants.ANIME_POSTER_CONTENT_DESCRIPTION,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Overlay with "No Trailer Available" text
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.6f)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = AppConstants.NO_TRAILER_AVAILABLE,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }
        }
    }
}

/**
 * Preview for TrailerPlayer with YouTube video.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun TrailerPlayerWithVideoPreview() {
    MaterialTheme {
        TrailerPlayer(
            youtubeId = "abc123",
            posterImageUrl = "https://example.com/poster.jpg"
        )
    }
}

/**
 * Preview for TrailerPlayer with poster image fallback.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun TrailerPlayerWithPosterPreview() {
    MaterialTheme {
        TrailerPlayer(
            youtubeId = null,
            posterImageUrl = "https://example.com/poster.jpg"
        )
    }
}
