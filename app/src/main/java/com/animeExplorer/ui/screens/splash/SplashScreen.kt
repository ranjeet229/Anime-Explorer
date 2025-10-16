package com.animeExplorer.ui.screens.splash

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animeExplorer.R
import com.animeExplorer.util.AppConstants
import kotlinx.coroutines.delay

/**
 * Beautiful splash screen with YouTube-like animations.
 * Features logo scaling, fade-in effects, and smooth transitions.
 * 
 * @param onSplashComplete Callback when splash animation is complete
 * @author udit
 */
@Composable
fun SplashScreen(
    onSplashComplete: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "alpha_animation"
    )
    
    val scaleAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.3f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = EaseOutCubic
        ),
        label = "scale_animation"
    )
    
    val sparkleAnim = rememberInfiniteTransition(label = "sparkle_animation")
    val sparkleAlpha by sparkleAnim.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle_alpha"
    )
    
    val sparkleScale by sparkleAnim.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        ),
        label = "sparkle_scale"
    )
    
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2500L)
        onSplashComplete()
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1A1A2E),
                        Color(0xFF16213E),
                        Color(0xFF0F3460)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        // Background sparkles
        Sparkle(
            modifier = Modifier
                .offset(x = (-80).dp, y = (-120).dp)
                .alpha(sparkleAlpha)
                .scale(sparkleScale),
            color = Color(0xFFFFD700)
        )
        
        Sparkle(
            modifier = Modifier
                .offset(x = 100.dp, y = (-80).dp)
                .alpha(sparkleAlpha * 0.7f)
                .scale(sparkleScale * 0.8f),
            color = Color(0xFFFF6B6B)
        )
        
        Sparkle(
            modifier = Modifier
                .offset(x = (-60).dp, y = 100.dp)
                .alpha(sparkleAlpha * 0.5f)
                .scale(sparkleScale * 0.6f),
            color = Color(0xFF4ECDC4)
        )
        
        // Main content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo with animations
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .alpha(alphaAnim.value)
                    .scale(scaleAnim.value),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.ic_splash_logo),
                    contentDescription = "Anime Explorer Logo",
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // App title with fade-in animation
            Text(
                text = AppConstants.ANIME_EXPLORER_TITLE,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.alpha(alphaAnim.value)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Subtitle
            Text(
                text = "Discover Amazing Anime",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.alpha(alphaAnim.value)
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Loading indicator
            CircularProgressIndicator(
                color = Color.White.copy(alpha = 0.6f),
                strokeWidth = 2.dp,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

/**
 * Sparkle component for background decoration.
 * 
 * @param modifier Modifier to apply to the sparkle
 * @param color Color of the sparkle
 * @author udit
 */
@Composable
private fun Sparkle(
    modifier: Modifier = Modifier,
    color: Color
) {
    Box(
        modifier = modifier.size(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(4.dp)
                .background(color = color, shape = CircleShape)
        )
    }
}

/**
 * Preview for SplashScreen component.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen(
            onSplashComplete = {}
        )
    }
}
