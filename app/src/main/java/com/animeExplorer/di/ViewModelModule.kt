package com.animeExplorer.di

import com.animeExplorer.data.repository.AnimeRepository
import com.animeExplorer.ui.viewmodel.AnimeDetailsViewModel
import com.animeExplorer.ui.viewmodel.AnimeListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Koin module for ViewModel-related dependencies.
 * @author Udit
 */
val viewModelModule = module {
    
    /**
     * Provides Main dispatcher for UI operations.
     * @return Main dispatcher instance.
     * @author Udit
     */
    single { Dispatchers.Main }
    
    /**
     * Provides AnimeListViewModel instance.
     * @return AnimeListViewModel instance.
     * @author Udit
     */
    viewModel<AnimeListViewModel> {
        AnimeListViewModel(
            repository = get<AnimeRepository>(),
            mainDispatcher = get()
        )
    }
    
    /**
     * Provides AnimeDetailsViewModel instance.
     * @return AnimeDetailsViewModel instance.
     * @author Udit
     */
    viewModel<AnimeDetailsViewModel> {
        AnimeDetailsViewModel(
            repository = get<AnimeRepository>(),
            mainDispatcher = get()
        )
    }
}
