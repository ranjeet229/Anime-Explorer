package com.animeExplorer.ui.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.animeExplorer.util.AppConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

/**
 * Composable for playing YouTube videos using the android-youtube-player library.
 * Provides loading states, error handling, and seamless video playback integration.
 * 
 * @param youtubeId The YouTube video ID to play (e.g., "dQw4w9WgXcQ")
 * @param modifier Optional modifier to apply to the component
 * @author udit
 */
@Composable
fun YouTubePlayer(
    youtubeId: String,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppConstants.VIDEO_PLAYER_HEIGHT.dp)
            .background(Color.Black)
    ) {
        if (error != null) {
            // Show error state
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(48.dp),
                    tint = Color.White
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = AppConstants.VIDEO_ERROR_TITLE,
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = error!!,
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )
            }
        } else if (isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(32.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = AppConstants.LOADING_VIDEO_MESSAGE,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        AndroidView(
            factory = { context ->
                createYouTubePlayerView(context, youtubeId, lifecycleOwner) { success, errorMessage ->
                    isLoading = false
                    if (!success) {
                        error = errorMessage
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

/**
 * Creates and configures a YouTubePlayerView for embedding YouTube videos.
 * Handles player lifecycle, video loading, and error management.
 * 
 * @param context The Android context for creating the player view
 * @param youtubeId The YouTube video ID to load and play
 * @param lifecycleOwner The LifecycleOwner to manage the player's lifecycle
 * @param onStateChange Callback function for player state changes (success/error)
 * @return Configured YouTubePlayerView instance ready for video playback
 * @author udit
 */
private fun createYouTubePlayerView(
    context: Context,
    youtubeId: String,
    lifecycleOwner: LifecycleOwner,
    onStateChange: (Boolean, String?) -> Unit
): YouTubePlayerView {
    return YouTubePlayerView(context).apply {
        lifecycleOwner.lifecycle.addObserver(this)

        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(youtubeId, 0f)
                onStateChange(true, null)
            }

            override fun onError(youTubePlayer: YouTubePlayer, error: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerError) {
                onStateChange(false, "${AppConstants.YOUTUBE_ERROR_PREFIX}${error.name}")
            }

            override fun onStateChange(youTubePlayer: YouTubePlayer, state: com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState) {
                when (state) {
                    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState.PLAYING -> {
                        onStateChange(true, null)
                    }
                    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState.ENDED -> {
                        onStateChange(true, null)
                    }
                    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState.PAUSED -> {
                        onStateChange(true, null)
                    }
                    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState.BUFFERING -> {
                        // Buffering state - no action needed
                    }
                    com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState.UNSTARTED -> {
                        // Unstarted state - no action needed
                    }
                    else -> {
                        onStateChange(true, null)
                    }
                }
            }
        })
    }
}

/**
 * Preview for YouTubePlayer component (shows loading state).
 * Note: Actual video playback requires a real device or emulator.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun YouTubePlayerPreview() {
    MaterialTheme {
        YouTubePlayer(
            youtubeId = "dQw4w9WgXcQ"
        )
    }
}
