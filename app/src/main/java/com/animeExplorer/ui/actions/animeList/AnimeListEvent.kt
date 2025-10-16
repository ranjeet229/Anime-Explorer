package com.animeExplorer.ui.actions.animeList

/**
 * Events that can be triggered from the anime list screen.
 * @author udit
 */
sealed class AnimeListEvent {
    
    /**
     * Event to navigate to anime details.
     * @param animeId MyAnimeList ID of the anime.
     * @author udit
     */
    data class NavigateToAnimeDetails(val animeId: Int) : AnimeListEvent()
}


