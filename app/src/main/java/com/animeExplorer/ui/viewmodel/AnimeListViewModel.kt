package com.animeExplorer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.animeExplorer.data.local.AnimeEntity
import com.animeExplorer.data.repository.AnimeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * State class representing the UI state for the anime list screen.
 * @author Udit
 */
data class AnimeListState(
    val animeList: List<AnimeEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false
)

/**
 * ViewModel for managing anime list screen state and business logic.
 * Handles loading, refreshing, and error states for the anime list.
 * Survives configuration changes and maintains state during screen rotations.
 * @param repository Repository for anime data operations.
 * @param mainDispatcher Dispatcher for UI operations.
 * @author Udit
 */
class AnimeListViewModel(
    private val repository: AnimeRepository,
    private val mainDispatcher: CoroutineDispatcher
) : ViewModel() {
    
    private val _state = MutableStateFlow(AnimeListState())
    val state: StateFlow<AnimeListState> = _state.asStateFlow()
    
    init {
        loadAnimeList()
    }
    
    /**
     * Loads the anime list from the repository.
     * Handles both initial loading and refresh scenarios.
     * @param forceRefresh If true, ignores cached data and fetches from API.
     * @author Udit
     */
    fun loadAnimeList(forceRefresh: Boolean = false) {
        viewModelScope.launch(mainDispatcher) {
            _state.value = _state.value.copy(
                isLoading = !forceRefresh,
                isRefreshing = forceRefresh,
                error = null
            )
            
            repository.getTopAnime(forceRefresh)
                .onSuccess { animeList ->
                    _state.value = _state.value.copy(
                        animeList = animeList,
                        isLoading = false,
                        isRefreshing = false,
                        error = null
                    )
                }
                .onFailure { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = exception.message ?: "Unknown error occurred"
                    )
                }
        }
    }
    
    /**
     * Refreshes the anime list by forcing a fresh API call.
     * This method is called when user manually refreshes the list.
     * @author Udit
     */
    fun refresh() {
        loadAnimeList(forceRefresh = true)
    }
}
