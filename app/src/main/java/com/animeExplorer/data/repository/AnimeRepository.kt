package com.animeExplorer.data.repository

import com.animeExplorer.data.local.AnimeDao
import com.animeExplorer.data.local.AnimeDetailsDao
import com.animeExplorer.data.local.AnimeDetailsEntity
import com.animeExplorer.data.local.AnimeEntity
import com.animeExplorer.data.remote.AnimeItemDto
import com.animeExplorer.data.remote.AnimeDetailsDto
import com.animeExplorer.data.remote.AnimeCharactersResponse

import com.animeExplorer.data.remote.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

/**
 * Repository class that manages anime data from both remote API and local database.
 * Implements the repository pattern to provide a clean API for data operations.
 * @param apiService Service for making API calls to API.
 * @param animeDao Data Access Object for anime list operations.
 * @param animeDetailsDao Data Access Object for anime details operations.
 * @param gson Gson instance for JSON serialization/deserialization.
 * @param ioDispatcher Dispatcher for I/O operations.
 * @param defaultDispatcher Dispatcher for CPU-intensive operations.
 * @author Udit
 */
class AnimeRepository(
    private val apiService: ApiService,
    private val animeDao: AnimeDao,
    private val animeDetailsDao: AnimeDetailsDao,
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher,
    private val defaultDispatcher: CoroutineDispatcher
) {
    
    /**
     * Fetches the top anime list with optional force refresh.
     * @param forceRefresh If true, ignores cached data and fetches from API.
     * @return Result containing list of anime entities or error.
     * @author Udit
     */
    suspend fun getTopAnime(forceRefresh: Boolean = false): Result<List<AnimeEntity>> = withContext(ioDispatcher) {
        try {
            if (!forceRefresh) {
                val cachedAnime = animeDao.getAllAnime()
                if (cachedAnime.isNotEmpty()) {
                    return@withContext Result.success(cachedAnime)
                }
            }
            
            val response = apiService.getTopAnime()
            val animeEntities = withContext(defaultDispatcher) {
                response.data.map { it.toAnimeEntity() }
            }
            animeDao.insertAll(animeEntities)
            Result.success(animeEntities)
        } catch (e: UnknownHostException) {
            val cachedAnime = animeDao.getAllAnime()
            if (cachedAnime.isNotEmpty()) {
                Result.success(cachedAnime)
            } else {
                Result.failure(e)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Fetches detailed information for a specific anime with optional force refresh.
     * @param animeId MyAnimeList ID of the anime.
     * @param forceRefresh If true, ignores cached data and fetches from API.
     * @return Result containing anime details entity or error.
     * @author Udit
     */
    suspend fun getAnimeDetails(animeId: Int, forceRefresh: Boolean = false): Result<AnimeDetailsEntity> = withContext(ioDispatcher) {
        try {
            if (!forceRefresh) {
                val cachedDetails = animeDetailsDao.getAnimeDetails(animeId)
                if (cachedDetails != null) {
                    return@withContext Result.success(cachedDetails)
                }
            }
            
            val detailsResponse = apiService.getAnimeDetails(animeId)
            val charactersResponse = try {
                apiService.getAnimeCharacters(animeId)
            } catch (_: Exception) {
                AnimeCharactersResponse(emptyList())
            }
            
            val animeDetailsEntity = withContext(defaultDispatcher) {
                detailsResponse.data.toAnimeDetailsEntity(
                    charactersResponse.data.map { it.character.name },
                    gson
                )
            }
            animeDetailsDao.insertAnimeDetails(animeDetailsEntity)
            Result.success(animeDetailsEntity)
        } catch (e: UnknownHostException) {
            val cachedDetails = animeDetailsDao.getAnimeDetails(animeId)
            if (cachedDetails != null) {
                Result.success(cachedDetails)
            } else {
                Result.failure(e)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    /**
     * Converts AnimeItemDto to AnimeEntity for database storage.
     * @return AnimeEntity instance.
     * @author Udit
     */
    private fun AnimeItemDto.toAnimeEntity(): AnimeEntity {
        return AnimeEntity(
            id = malId,
            title = title,
            episodes = episodes,
            score = score,
            imageUrl = images.jpg.imageUrl,
            largeImageUrl = images.jpg.largeImageUrl,
            youtubeId = trailer?.youtubeId
        )
    }
    
    /**
     * Converts AnimeDetailsDto to AnimeDetailsEntity for database storage.
     * @param characters List of character names.
     * @param gson Gson instance for JSON serialization.
     * @return AnimeDetailsEntity instance.
     * @author Udit
     */
    private fun AnimeDetailsDto.toAnimeDetailsEntity(
        characters: List<String>,
        gson: Gson
    ): AnimeDetailsEntity {
        return AnimeDetailsEntity(
            id = malId,
            title = title,
            synopsis = synopsis,
            episodes = episodes,
            score = score,
            imageUrl = images.jpg.imageUrl,
            largeImageUrl = images.jpg.largeImageUrl,
            youtubeId = trailer?.youtubeId,
            genres = gson.toJson(genres.map { it.name }),
            characters = gson.toJson(characters)
        )
    }
}
