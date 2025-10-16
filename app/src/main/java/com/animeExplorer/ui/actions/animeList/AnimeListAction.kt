package com.animeExplorer.ui.actions.animeList

/**
 * Actions that can be performed from the anime list screen.
 * @author udit
 */
sealed class AnimeListAction {
    
    /**
     * Action to load the anime list.
     * @param forceRefresh If true, ignores cached data and fetches from API.
     * @author udit
     */
    data class LoadAnimeList(val forceRefresh: Boolean = false) : AnimeListAction()
    
    /**
     * Action to retry loading anime list after an error.
     * @author udit
     */
    object RetryLoadAnimeList : AnimeListAction()
}


