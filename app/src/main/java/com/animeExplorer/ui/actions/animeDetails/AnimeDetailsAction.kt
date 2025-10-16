package com.animeExplorer.ui.actions.animeDetails

/**
 * Actions that can be performed from the anime details screen.
 * @author udit
 */
sealed class AnimeDetailsAction {
    
    /**
     * Action to load anime details.
     * @param animeId MyAnimeList ID of the anime.
     * @param forceRefresh If true, ignores cached data and fetches from API.
     * @author udit
     */
    data class LoadAnimeDetails(val animeId: Int, val forceRefresh: Boolean = false) : AnimeDetailsAction()
    
    /**
     * Action to retry loading anime details after an error.
     * @param animeId MyAnimeList ID of the anime.
     * @author udit
     */
    data class RetryLoadAnimeDetails(val animeId: Int) : AnimeDetailsAction()
}


