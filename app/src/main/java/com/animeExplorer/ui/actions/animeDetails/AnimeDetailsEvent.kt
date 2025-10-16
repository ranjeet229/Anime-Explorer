package com.animeExplorer.ui.actions.animeDetails

/**
 * Events that can be triggered from the anime details screen.
 * @author udit
 */
sealed class AnimeDetailsEvent {
    
    /**
     * Event to navigate back to previous screen.
     * @author udit
     */
    object NavigateBack : AnimeDetailsEvent()
}


