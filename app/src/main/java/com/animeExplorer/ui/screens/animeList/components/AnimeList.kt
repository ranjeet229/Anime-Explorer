package com.animeExplorer.ui.screens.animeList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.animeExplorer.data.local.AnimeEntity
import com.animeExplorer.ui.animations.FadeInAnimation
import com.animeExplorer.ui.animations.SlideInAnimation
import com.animeExplorer.util.AppConstants

/**
 * Lazy column displaying the list of anime with modern styling and animations.
 * 
 * @param animeList List of anime entities to display in the list
 * @param onAnimeClick Callback function triggered when an anime item is clicked, receives the anime ID
 * @author udit
 */
@Composable
fun AnimeList(
    animeList: List<AnimeEntity>,
    onAnimeClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentPadding = PaddingValues(AppConstants.DEFAULT_PADDING.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(
            items = animeList,
            key = { _, anime -> anime.id }
        ) { _, anime ->
            SlideInAnimation(visible = true) {
                FadeInAnimation(visible = true) {
                    AnimeCard(
                        anime = anime,
                        onClick = { onAnimeClick(anime.id) }
                    )
                }
            }
        }
    }
}

/**
 * Preview for AnimeList component.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeListPreview() {
    val sampleAnimeList = listOf(
        AnimeEntity(
            id = 1,
            title = "Attack on Titan: The Final Season",
            imageUrl = "https://example.com/image1.jpg",
            largeImageUrl = "https://example.com/large-image1.jpg",
            episodes = 12,
            score = 9.2f,
            youtubeId = "abc123"
        ),
        AnimeEntity(
            id = 2,
            title = "Demon Slayer",
            imageUrl = "https://example.com/image2.jpg",
            largeImageUrl = "https://example.com/large-image2.jpg",
            episodes = 26,
            score = 8.9f,
            youtubeId = "def456"
        ),
        AnimeEntity(
            id = 3,
            title = "Jujutsu",
            imageUrl = "https://example.com/image3.jpg",
            largeImageUrl = "https://example.com/large-image3.jpg",
            episodes = 24,
            score = 8.7f,
            youtubeId = "ghi789"
        )
    )
    
    MaterialTheme {
        AnimeList(
            animeList = sampleAnimeList,
            onAnimeClick = {}
        )
    }
}
