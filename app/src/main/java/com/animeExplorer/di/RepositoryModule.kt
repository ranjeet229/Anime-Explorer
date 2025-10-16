package com.animeExplorer.di

import com.animeExplorer.data.local.AnimeDao
import com.animeExplorer.data.local.AnimeDetailsDao
import com.animeExplorer.data.remote.ApiService
import com.animeExplorer.data.repository.AnimeRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

/**
 * Koin module for repository-related dependencies.
 * @author Udit
 */
val repositoryModule = module {
    
    /**
     * Provides IO dispatcher for I/O operations.
     * @return IO dispatcher instance.
     * @author Udit
     */
    single { Dispatchers.IO }
    
    /**
     * Provides Default dispatcher for CPU-intensive operations.
     * @return Default dispatcher instance.
     * @author Udit
     */
    single { Dispatchers.Default }
    
    /**
     * Provides AnimeRepository instance.
     * @return AnimeRepository instance.
     * @author Udit
     */
    single<AnimeRepository> {
        AnimeRepository(
            apiService = get<ApiService>(),
            animeDao = get<AnimeDao>(),
            animeDetailsDao = get<AnimeDetailsDao>(),
            gson = get<Gson>(),
            ioDispatcher = get(),
            defaultDispatcher = get()
        )
    }
}
