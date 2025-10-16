package com.animeExplorer.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animeExplorer.ui.animations.BounceAnimation
import com.animeExplorer.ui.animations.FadeInAnimation
import com.animeExplorer.util.AppConstants

/**
 * Reusable error content component with modern design and animations.
 * 
 * @param error Error message to display
 * @param onRetry Callback function triggered when retry button is clicked
 * @param modifier Optional modifier to apply to the component
 * @author udit
 */
@Composable
fun ErrorContent(
    error: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    BounceAnimation(visible = true) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FadeInAnimation(visible = true) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.error
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            FadeInAnimation(visible = true) {
                Text(
                    text = AppConstants.OOPS_MESSAGE,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            FadeInAnimation(visible = true) {
                Text(
                    text = error,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            FadeInAnimation(visible = true) {
                Button(
                    onClick = onRetry,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    modifier = Modifier.height(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = AppConstants.RETRY_TEXT,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * Preview for ErrorContent component.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun ErrorContentPreview() {
    MaterialTheme {
        ErrorContent(
            error = AppConstants.NETWORK_ERROR_MESSAGE,
            onRetry = {}
        )
    }
}

/**
 * Preview for ErrorContent with short error message.
 * 
 * @author udit
 */
@Preview(showBackground = true)
@Composable
fun ErrorContentShortMessagePreview() {
    MaterialTheme {
        ErrorContent(
            error = AppConstants.ERROR_TEXT,
            onRetry = {}
        )
    }
}

