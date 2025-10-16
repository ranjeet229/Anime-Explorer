package com.animeExplorer.di

import android.content.Context
import androidx.room.Room
import com.animeExplorer.util.AppConstants
import com.animeExplorer.data.local.AnimeDao
import com.animeExplorer.data.local.AnimeDatabase
import com.animeExplorer.data.local.AnimeDetailsDao
import org.koin.dsl.module

/**
 * Koin module for database-related dependencies.
 * @author Udit
 */
val databaseModule = module {
    
    /**
     * Provides Room database instance.
     * @return Configured AnimeDatabase instance.
     * @author Udit
     */
    single<AnimeDatabase> {
        Room.databaseBuilder(
            get<Context>(),
            AnimeDatabase::class.java,
            AppConstants.DATABASE_NAME
        ).build()
    }
    
    /**
     * Provides AnimeDao for anime list operations.
     * @return AnimeDao instance.
     * @author Udit
     */
    single<AnimeDao> {
        get<AnimeDatabase>().animeDao()
    }
    
    /**
     * Provides AnimeDetailsDao for anime details operations.
     * @return AnimeDetailsDao instance.
     * @author Udit
     */
    single<AnimeDetailsDao> {
        get<AnimeDatabase>().animeDetailsDao()
    }
}
