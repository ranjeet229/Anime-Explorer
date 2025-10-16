package com.animeExplorer

import android.app.Application
import com.animeExplorer.di.databaseModule
import com.animeExplorer.di.networkModule
import com.animeExplorer.di.repositoryModule
import com.animeExplorer.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

/**
 * Application class for AnimeExplorer app.
 * Initializes Koin dependency injection.
 * @author Udit
 */
class AnimeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AnimeApplication)
            modules(
                listOf(
                    networkModule,
                    databaseModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}
