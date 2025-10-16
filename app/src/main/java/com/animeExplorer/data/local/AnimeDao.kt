package com.animeExplorer.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Data Access Object (DAO) for anime list operations in the local database.
 * Provides methods to interact with the anime table for CRUD operations.
 * @author Udit
 */
@Dao
interface AnimeDao {
    
    /**
     * Retrieves all anime entities from the local database.
     * @return List of all anime entities stored in the database.
     * @author Udit
     */
    @Query("SELECT * FROM anime")
    suspend fun getAllAnime(): List<AnimeEntity>

    /**
     * Inserts or updates a list of anime entities in the database.
     * Uses REPLACE strategy to handle conflicts by replacing existing records.
     * @param anime List of anime entities to insert or update.
     * @author Udit
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(anime: List<AnimeEntity>)

    /**
     * Deletes all anime entities from the database.
     * This method clears the entire anime table.
     * @author Udit
     */
    @Query("DELETE FROM anime")
    suspend fun deleteAll()
}
