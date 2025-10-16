package com.animeExplorer.ui.screens.animeDetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.animeExplorer.data.local.AnimeDetailsEntity
import com.animeExplorer.ui.actions.animeDetails.AnimeDetailsAction
import com.animeExplorer.ui.actions.animeDetails.AnimeDetailsEvent
import com.animeExplorer.ui.actions.animeDetails.AnimeDetailsUiState
import com.animeExplorer.ui.components.ErrorContent
import com.animeExplorer.ui.screens.animeDetails.components.AnimeDetailsContent
import com.animeExplorer.util.AppConstants

/**
 * Main screen for displaying detailed information about a specific anime.
 * 
 * @param animeId Unique identifier of the anime to display details for
 * @param uiState Current UI state of the screen (LOADING, SUCCESS, ERROR)
 * @param animeDetails Anime details entity containing comprehensive information about the anime
 * @param error Error message to display when in ERROR state
 * @param onAction Callback function for handling screen actions like loading and retry
 * @param onEvent Callback function for handling navigation events like going back
 * @author udit
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeDetailsScreen(
    animeId: Int,
    uiState: AnimeDetailsUiState,
    animeDetails: AnimeDetailsEntity?,
    error: String?,
    onAction: (AnimeDetailsAction) -> Unit,
    onEvent: (AnimeDetailsEvent) -> Unit
) {
    LaunchedEffect(animeId) {
        onAction(AnimeDetailsAction.LoadAnimeDetails(animeId))
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = AppConstants.ANIME_DETAILS_TITLE,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = {
                        onEvent(AnimeDetailsEvent.NavigateBack)
                    }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = AppConstants.BACK_BUTTON_CONTENT_DESCRIPTION
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
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
                AnimeDetailsUiState.LOADING -> {
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
                            text = AppConstants.LOADING_DETAILS_MESSAGE,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                AnimeDetailsUiState.ERROR -> {
                    Card(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        ErrorContent(
                            error = error ?: AppConstants.ERROR_TEXT,
                            onRetry = { onAction(AnimeDetailsAction.RetryLoadAnimeDetails(animeId)) },
                            modifier = Modifier.padding(24.dp)
                        )
                    }
                }
                AnimeDetailsUiState.SUCCESS -> {
                    animeDetails?.let { details ->
                        AnimeDetailsContent(
                            animeDetails = details,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

/**
 * Preview for AnimeDetailsScreen in loading state.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeDetailsScreenLoadingPreview() {
    MaterialTheme {
        AnimeDetailsScreen(
            animeId = 1,
            uiState = AnimeDetailsUiState.LOADING,
            animeDetails = null,
            error = null,
            onAction = {},
            onEvent = {}
        )
    }
}

/**
 * Preview for AnimeDetailsScreen in error state.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun AnimeDetailsScreenErrorPreview() {
    MaterialTheme {
        AnimeDetailsScreen(
            animeId = 1,
            uiState = AnimeDetailsUiState.ERROR,
            animeDetails = null,
            error = AppConstants.ANIME_DETAILS_ERROR_MESSAGE,
            onAction = {},
            onEvent = {}
        )
    }
}


