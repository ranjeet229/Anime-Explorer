package com.animeExplorer.ui.screens.animeList.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import com.animeExplorer.data.local.AnimeEntity
import com.animeExplorer.R
import com.animeExplorer.ui.animations.FadeInAnimation
import com.animeExplorer.ui.animations.ScaleAnimation
import com.animeExplorer.ui.animations.SlideInAnimation
import com.animeExplorer.util.AppConstants
import java.util.Locale

/**
 * Card component displaying anime information with modern design and animations.
 * 
 * @param anime Anime entity containing title, episodes, score, and image URLs
 * @param onClick Callback function triggered when the card is clicked
 * @author udit
 */
@Composable
fun AnimeCard(
    anime: AnimeEntity,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 150),
        label = "card_scale"
    )
    
    FadeInAnimation(visible = true) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppConstants.CARD_HEIGHT.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .clickable(
                    onClick = {
                        isPressed = true
                        onClick()
                    }
                ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                AnimeCardImage(anime = anime)
                AnimeCardContent(anime = anime)
            }
        }
    }
}

/**
 * Image section of the anime card with gradient overlay and slide-in animation.
 * 
 * @param anime Anime entity containing image URLs
 * @author udit
 */
@Composable
private fun AnimeCardImage(anime: AnimeEntity) {
    SlideInAnimation(visible = true) {
        Box(
            modifier = Modifier
                .width(AppConstants.IMAGE_WIDTH.dp)
                .fillMaxHeight()
        ) {
            AsyncImage(
                model = if (AppConstants.SHOW_ANIME_IMAGES) (anime.imageUrl ?: anime.largeImageUrl) else null,
                contentDescription = anime.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = if (anime.imageUrl != null || anime.largeImageUrl != null) ContentScale.Crop else ContentScale.Fit,
                error = painterResource(id = R.drawable.ic_anime_placeholder),
                placeholder = painterResource(id = R.drawable.ic_anime_placeholder)
            )
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.3f)
                            )
                        )
                    )
            )
        }
    }
}

/**
 * Content section of the anime card containing title, episodes, and rating.
 * 
 * @param anime Anime entity containing title, episodes, and score
 * @author udit
 */
@Composable
private fun AnimeCardContent(anime: AnimeEntity) {
    ScaleAnimation(visible = true) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(AppConstants.DEFAULT_PADDING.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    AnimeCardTitle(title = anime.title)
                    Spacer(modifier = Modifier.height(8.dp))
                    AnimeCardEpisodes(episodes = anime.episodes)
                }
                AnimeCardRating(score = anime.score)
            }
        }
    }
}

/**
 * Title section of the anime card with fade-in animation.
 * 
 * @param title Anime title to display
 * @author udit
 */
@Composable
private fun AnimeCardTitle(title: String) {
    FadeInAnimation(visible = true) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 22.sp
        )
    }
}

/**
 * Episodes information section of the anime card with scale animation.
 * 
 * @param episodes Number of episodes, null if not available
 * @author udit
 */
@Composable
private fun AnimeCardEpisodes(episodes: Int?) {
    episodes?.let { episodeCount ->
        ScaleAnimation(visible = true) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$episodeCount ${if (episodeCount == 1) AppConstants.EPISODE_SINGULAR else AppConstants.EPISODE_PLURAL}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Rating section of the anime card with bounce animation.
 * 
 * @param score Anime rating score, null if not available
 * @author udit
 */
@Composable
private fun AnimeCardRating(score: Float?) {
    score?.let { rating ->
        if (rating > 0) {
            FadeInAnimation(visible = true) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(Locale.US, AppConstants.RATING_FORMAT, rating),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

/**
 * Preview for AnimeCard component.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeCardPreview() {
    MaterialTheme {
        AnimeCard(
            anime = AnimeEntity(
                id = 1,
                title = "Attack on Titan: The Final Season",
                imageUrl = "https://example.com/image.jpg",
                largeImageUrl = "https://example.com/large-image.jpg",
                episodes = 12,
                score = 9.2f,
                youtubeId = "abc123"
            ),
            onClick = {}
        )
    }
}

/**
 * Preview for AnimeCard with long title.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeCardLongTitlePreview() {
    MaterialTheme {
        AnimeCard(
            anime = AnimeEntity(
                id = 2,
                title = "This is a very long anime title that should be truncated with ellipsis when it exceeds the available space",
                imageUrl = "https://example.com/image.jpg",
                largeImageUrl = "https://example.com/large-image.jpg",
                episodes = 24,
                score = 8.7f,
                youtubeId = "def456"
            ),
            onClick = {}
        )
    }
}


