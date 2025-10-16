package com.animeExplorer.data.remote

import com.animeExplorer.util.AppConstants
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Retrofit service interface for API endpoints.
 * Provides methods to fetch anime data from the API.
 * @author Udit
 */
interface ApiService {

    /**
     * Fetches the top anime list from API.
     * @return TopAnimeResponse containing list of top anime.
     * @author Udit
     */
    @GET(AppConstants.TOP_ANIME_ENDPOINT)
    suspend fun getTopAnime(): TopAnimeResponse

    /**
     * Fetches detailed information for a specific anime.
     * @param id MyAnimeList ID of the anime.
     * @return AnimeDetailsResponse containing detailed anime information.
     * @author Udit
     */
    @GET(AppConstants.ANIME_DETAILS_ENDPOINT)
    suspend fun getAnimeDetails(@Path("id") id: Int): AnimeDetailsResponse

    /**
     * Fetches character information for a specific anime.
     * @param id MyAnimeList ID of the anime.
     * @return AnimeCharactersResponse containing character information.
     * @author Udit
     */
    @GET(AppConstants.ANIME_CHARACTERS_ENDPOINT)
    suspend fun getAnimeCharacters(@Path("id") id: Int): AnimeCharactersResponse


}


