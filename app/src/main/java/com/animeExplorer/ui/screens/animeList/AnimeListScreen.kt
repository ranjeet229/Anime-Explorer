package com.animeExplorer.ui.screens.animeList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animeExplorer.data.local.AnimeEntity
import com.animeExplorer.ui.actions.animeList.AnimeListAction
import com.animeExplorer.ui.actions.animeList.AnimeListEvent
import com.animeExplorer.ui.actions.animeList.AnimeListUiState
import com.animeExplorer.ui.animations.BounceAnimation
import com.animeExplorer.ui.animations.FadeInAnimation
import com.animeExplorer.ui.components.ErrorContent
import com.animeExplorer.ui.screens.animeList.components.AnimeList
import com.animeExplorer.util.AppConstants

/**
 * Main screen for displaying the list of anime with modern UI design and animations.
 *
 * @param uiState Current UI state of the screen (LOADING, SUCCESS, ERROR)
 * @param animeList List of anime entities to display when in SUCCESS state
 * @param error Error message to display when in ERROR state
 * @param onAction Callback function for handling screen actions like loading and retry
 * @param onEvent Callback function for handling navigation events
 * @author udit
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    uiState: AnimeListUiState,
    animeList: List<AnimeEntity>,
    error: String?,
    onAction: (AnimeListAction) -> Unit,
    onEvent: (AnimeListEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onAction(AnimeListAction.LoadAnimeList())
    }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = null,
                            modifier = Modifier.size(28.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = AppConstants.ANIME_EXPLORER_TITLE,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.surface,
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    )
                )
                .padding(padding)
        ) {
            when (uiState) {
                AnimeListUiState.LOADING -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(48.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = AppConstants.LOADING_ANIME_MESSAGE,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                AnimeListUiState.ERROR -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        BounceAnimation(visible = true) {
                            Card(
                                modifier = Modifier.padding(24.dp),
                                shape = RoundedCornerShape(16.dp),
                                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                            ) {
                                ErrorContent(
                                    error = error ?: AppConstants.ERROR_TEXT,
                                    onRetry = { onAction(AnimeListAction.RetryLoadAnimeList) },
                                    modifier = Modifier.padding(24.dp)
                                )
                            }
                        }
                    }
                }

                AnimeListUiState.SUCCESS -> {
                    FadeInAnimation(visible = true) {
                        AnimeList(
                            animeList = animeList,
                            onAnimeClick = { animeId ->
                                onEvent(AnimeListEvent.NavigateToAnimeDetails(animeId))
                            }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Preview for AnimeListScreen in loading state.
 *
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeListScreenLoadingPreview() {
    MaterialTheme {
        AnimeListScreen(
            uiState = AnimeListUiState.LOADING,
            animeList = emptyList(),
            error = null,
            onAction = {},
            onEvent = {}
        )
    }
}

/**
 * Preview for AnimeListScreen in success state.
 *
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeListScreenSuccessPreview() {
    MaterialTheme {
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
            )
        )

        AnimeListScreen(
            uiState = AnimeListUiState.SUCCESS,
            animeList = sampleAnimeList,
            error = null,
            onAction = {},
            onEvent = {}
        )
    }
}

/**
 * Preview for AnimeListScreen in error state.
 *
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeListScreenErrorPreview() {
    MaterialTheme {
        AnimeListScreen(
            uiState = AnimeListUiState.ERROR,
            animeList = emptyList(),
            error = AppConstants.ANIME_LIST_ERROR_MESSAGE,
            onAction = {},
            onEvent = {}
        )
    }
}


