package com.animeExplorer.ui.screens.animeDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animeExplorer.data.local.AnimeDetailsEntity
import com.animeExplorer.ui.components.TrailerPlayer
import com.animeExplorer.util.AppConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Locale

/**
 * Content component for displaying comprehensive anime details with modern design.
 * 
 * @param animeDetails Anime details entity containing all information about the anime
 * @param modifier Optional modifier to apply to the component
 * @author udit
 */
@Composable
fun AnimeDetailsContent(
    animeDetails: AnimeDetailsEntity,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        TrailerPlayer(
            youtubeId = animeDetails.youtubeId,
            posterImageUrl = animeDetails.largeImageUrl ?: animeDetails.imageUrl,
            modifier = Modifier.fillMaxWidth()
        )
        
        Column(
            modifier = Modifier.padding(AppConstants.DEFAULT_PADDING.dp)
        ) {
            AnimeTitle(animeDetails.title)
            AnimeStats(animeDetails)
            AnimeSynopsis(animeDetails.synopsis)
            AnimeGenres(animeDetails.genres)
            AnimeCharacters(animeDetails.characters)
        }
    }
}

/**
 * Displays the anime title with prominent styling.
 * 
 * @param title Title of the anime to display
 * @author udit
 */
@Composable
private fun AnimeTitle(title: String) {
    Text(
        text = title,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )
    
    Spacer(modifier = Modifier.height(16.dp))
}

/**
 * Displays anime statistics including rating and episode count with icons.
 * 
 * @param animeDetails Anime details entity containing score and episodes information
 * @author udit
 */
@Composable
private fun AnimeStats(animeDetails: AnimeDetailsEntity) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        animeDetails.score?.let { score ->
            if (score > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = String.format(Locale.US, AppConstants.RATING_FORMAT, score),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
        
        animeDetails.episodes?.let { episodes ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "$episodes ${if (episodes == 1) AppConstants.EPISODE_SINGULAR else AppConstants.EPISODE_PLURAL}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
    
    Spacer(modifier = Modifier.height(24.dp))
}

/**
 * Displays anime synopsis with proper formatting and styling.
 * 
 * @param synopsis Synopsis text describing the anime plot
 * @author udit
 */
@Composable
private fun AnimeSynopsis(synopsis: String?) {
    synopsis?.let { text ->
        Text(
            text = AppConstants.SYNOPSIS_LABEL,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = text,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * Displays anime genres as filter chips in a horizontal scrollable row.
 * 
 * @param genresJson JSON string containing list of genre names
 * @author udit
 */
@Composable
private fun AnimeGenres(genresJson: String?) {
    val genres = parseJsonList(genresJson)
    
    if (genres.isNotEmpty()) {
        Text(
            text = AppConstants.GENRES_LABEL,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(genres.size) { index ->
                FilterChip(
                    onClick = { },
                    label = { Text(genres[index]) },
                    selected = false,
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

/**
 * Displays main cast/characters of the anime in a list format.
 * 
 * @param charactersJson JSON string containing list of character names
 * @author udit
 */
@Composable
private fun AnimeCharacters(charactersJson: String?) {
    val characters = parseJsonList(charactersJson)
    
    if (characters.isNotEmpty()) {
        Text(
            text = AppConstants.MAIN_CAST_LABEL,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        characters.take(10).forEach { character ->
            Text(
                text = "${AppConstants.CHARACTER_BULLET}$character",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}

/**
 * Parses JSON string to list of strings for genres and characters.
 * 
 * @param jsonString JSON string to parse into a list
 * @return List of strings or empty list if parsing fails
 * @author udit
 */
private fun parseJsonList(jsonString: String?): List<String> {
    return try {
        val type = object : TypeToken<List<String>>() {}.type
        Gson().fromJson<List<String>>(jsonString, type) ?: emptyList()
    } catch (_: Exception) {
        emptyList()
    }
}

/**
 * Preview for AnimeDetailsContent component with sample data.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeDetailsContentPreview() {
    MaterialTheme {
        val sampleAnimeDetails = AnimeDetailsEntity(
            id = 1,
            title = "Attack on Titan: The Final Season",
            imageUrl = "https://example.com/image.jpg",
            largeImageUrl = "https://example.com/large-image.jpg",
            synopsis = "The story follows Eren Yeager, a young man whose family was killed by Titans. He joins the Survey Corps to fight against the Titans and uncover the truth about their existence.",
            score = 9.2f,
            episodes = 12,
            youtubeId = "abc123",
            genres = "[\"Action\", \"Drama\", \"Fantasy\"]",
            characters = "[\"Eren Yeager\", \" Ackerman Shiatsu\", \"Armin Sheri\"]"
        )
        
        AnimeDetailsContent(
            animeDetails = sampleAnimeDetails
        )
    }
}
