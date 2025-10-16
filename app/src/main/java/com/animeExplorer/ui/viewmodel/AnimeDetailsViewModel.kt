package com.animeExplorer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animeExplorer.data.local.AnimeDetailsEntity
import com.animeExplorer.data.repository.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * State class representing the UI state for the anime details screen.
 * @author Udit
 */
data class AnimeDetailsState(
    val animeDetails: AnimeDetailsEntity? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)

/**
 * ViewModel for managing anime details screen state and business logic.
 * Handles loading, caching, and error states for anime details.
 * Survives configuration changes and maintains state during screen rotations.
 * @param repository Repository for anime data operations.
 * @param mainDispatcher Dispatcher for UI operations.
 * @author Udit
 */
class AnimeDetailsViewModel(
    private val repository: AnimeRepository,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {
    
    private val _state = MutableStateFlow(AnimeDetailsState())
    val state: StateFlow<AnimeDetailsState> = _state.asStateFlow()
    
    /**
     * Loads detailed anime information from the repository.
     * Handles both initial loading and refresh scenarios.
     * @param animeId MyAnimeList ID of the anime to load details for.
     * @param forceRefresh If true, ignores cached data and fetches from API.
     * @author Udit
     */
    fun loadAnimeDetails(animeId: Int, forceRefresh: Boolean = false) {
        viewModelScope.launch(mainDispatcher) {
            _state.value = _state.value.copy(
                isLoading = !forceRefresh,
                isRefreshing = forceRefresh,
                error = null
            )
            
            repository.getAnimeDetails(animeId, forceRefresh)
                .onSuccess { animeDetails ->
                    _state.value = _state.value.copy(
                        animeDetails = animeDetails,
                        isLoading = false,
                        isRefreshing = false,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = exception.message ?: "Failed to load anime details"
                    )
                }
        }
    }
    
    /**
     * Refreshes the anime details by forcing a fresh API call.
     * This method is called when user manually refreshes the details.
     * @param animeId MyAnimeList ID of the anime to refresh details for.
     * @author Udit
     */
    fun refreshAnimeDetails(animeId: Int) {
        loadAnimeDetails(animeId, forceRefresh = true)
    }
}
