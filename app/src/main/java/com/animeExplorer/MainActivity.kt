package com.animeExplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.animeExplorer.ui.actions.animeDetails.AnimeDetailsAction
import com.animeExplorer.ui.actions.animeDetails.AnimeDetailsEvent
import com.animeExplorer.ui.actions.animeDetails.AnimeDetailsUiState
import com.animeExplorer.ui.actions.animeList.AnimeListAction
import com.animeExplorer.ui.actions.animeList.AnimeListEvent
import com.animeExplorer.ui.actions.animeList.AnimeListUiState
import com.animeExplorer.ui.screens.animeDetails.AnimeDetailsScreen
import com.animeExplorer.ui.screens.animeList.AnimeListScreen
import com.animeExplorer.ui.screens.splash.SplashScreen
import com.animeExplorer.ui.theme.AnimeExplorerTheme
import com.animeExplorer.ui.viewmodel.AnimeDetailsViewModel
import com.animeExplorer.ui.viewmodel.AnimeListViewModel
import com.animeExplorer.util.AppConstants
import org.koin.androidx.compose.koinViewModel

/**
 * Main activity of the AnimeExplorer application.
 * Sets up navigation and theme for the app.
 * @author Udit
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeExplorerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AnimeNavigation(navController)
                }
            }
        }
    }
}

/**
 * Navigation component that sets up the navigation graph.
 * @param navController Navigation controller for handling navigation.
 * @author Udit
 */
@Composable
private fun AnimeNavigation(navController: NavHostController) {
    var showSplash by rememberSaveable { mutableStateOf(true) }
    
    if (showSplash) {
        SplashScreen(
            onSplashComplete = {
                showSplash = false
            }
        )
    } else {
        NavHost(
            navController = navController,
            startDestination = AppConstants.ANIME_LIST_ROUTE
        ) {
            composable(AppConstants.ANIME_LIST_ROUTE) {
                AnimeListRoute(navController)
            }
            
            composable("${AppConstants.ANIME_DETAILS_ROUTE}/{${AppConstants.ANIME_ID_PARAM}}") { backStackEntry ->
                val animeId = backStackEntry.arguments?.getString(AppConstants.ANIME_ID_PARAM)?.toIntOrNull() ?: 0
                AnimeDetailsRoute(animeId, navController)
            }
        }
    }
}

/**
 * Anime list route composable.
 * @param navController Navigation controller for navigation.
 * @author Udit
 */
@Composable
private fun AnimeListRoute(navController: NavHostController) {
    val viewModel: AnimeListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    val uiState = when {
        state.isLoading || state.isRefreshing -> AnimeListUiState.LOADING
        state.error != null -> AnimeListUiState.ERROR
        else -> AnimeListUiState.SUCCESS
    }
    
    AnimeListScreen(
        uiState = uiState,
        animeList = state.animeList,
        error = state.error,
        onAction = { action ->
            when (action) {
                is AnimeListAction.LoadAnimeList -> viewModel.loadAnimeList(action.forceRefresh)
                is AnimeListAction.RetryLoadAnimeList -> viewModel.refresh()
            }
        },
        onEvent = { event ->
            when (event) {
                is AnimeListEvent.NavigateToAnimeDetails -> {
                    navController.navigate("${AppConstants.ANIME_DETAILS_ROUTE}/${event.animeId}")
                }
            }
        }
    )
}

/**
 * Anime details route composable.
 * @param animeId ID of the anime to display details for.
 * @param navController Navigation controller for navigation.
 * @author Udit
 */
@Composable
private fun AnimeDetailsRoute(animeId: Int, navController: NavHostController) {
    val viewModel: AnimeDetailsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    val uiState = when {
        state.isLoading || state.isRefreshing -> AnimeDetailsUiState.LOADING
        state.error != null -> AnimeDetailsUiState.ERROR
        else -> AnimeDetailsUiState.SUCCESS
    }
    
    AnimeDetailsScreen(
        animeId = animeId,
        uiState = uiState,
        animeDetails = state.animeDetails,
        error = state.error,
        onAction = { action ->
            when (action) {
                is AnimeDetailsAction.LoadAnimeDetails -> viewModel.loadAnimeDetails(action.animeId, action.forceRefresh)
                is AnimeDetailsAction.RetryLoadAnimeDetails -> viewModel.refreshAnimeDetails(action.animeId)
            }
        },
        onEvent = { event ->
            when (event) {
                is AnimeDetailsEvent.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    )
}