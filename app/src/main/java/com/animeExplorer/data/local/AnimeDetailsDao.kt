package com.animeExplorer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object (DAO) for anime details operations in the local database.
 * Provides methods to interact with the anime_details table for CRUD operations.
 * @author Udit
 */
@Dao
interface AnimeDetailsDao {
    
    /**
     * Retrieves detailed anime information by ID from the local database.
     * @param animeId MyAnimeList ID of the anime to retrieve details for.
     * @return AnimeDetailsEntity if found, null otherwise.
     * @author Udit
     */
    @Query("SELECT * FROM anime_details WHERE id = :animeId")
    suspend fun getAnimeDetails(animeId: Int): AnimeDetailsEntity?

    /**
     * Inserts or updates anime details in the database.
     * Uses REPLACE strategy to handle conflicts by replacing existing records.
     * @param animeDetails Anime details entity to insert or update.
     * @author Udit
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnimeDetails(animeDetails: AnimeDetailsEntity)
}
