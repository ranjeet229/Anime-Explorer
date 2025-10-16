package com.animeExplorer.data.remote

import com.animeExplorer.util.AppConstants
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Response wrapper for top anime list from API.
 * @author Udit
 */
data class TopAnimeResponse(
    val data: List<AnimeItemDto>
) : Serializable

/**
 * Data transfer object for individual anime items in the list.
 * @author Udit
 */
data class AnimeItemDto(
    @SerializedName(AppConstants.SERIAL_KEY_MAL_ID) val malId: Int,
    val title: String,
    val episodes: Int?,
    val score: Float?,
    val images: ImagesDto,
    val trailer: TrailerDto?
) : Serializable

/**
 * Data transfer object for anime images.
 * @author Udit
 */
data class ImagesDto(
    val jpg: ImageTypeDto
) : Serializable

/**
 * Data transfer object for image type with different resolutions.
 * @author Udit
 */
data class ImageTypeDto(
    @SerializedName(AppConstants.SERIAL_KEY_IMAGE_URL) val imageUrl: String?,
    @SerializedName(AppConstants.SERIAL_KEY_LARGE_IMAGE_URL) val largeImageUrl: String?
) : Serializable

/**
 * Data transfer object for anime trailer information.
 * @author Udit
 */
data class TrailerDto(
    val url: String?,
    @SerializedName(AppConstants.SERIAL_KEY_YOUTUBE_ID) val youtubeId: String?
) : Serializable

/**
 * Response wrapper for anime details from API.
 * @author Udit
 */
data class AnimeDetailsResponse(
    val data: AnimeDetailsDto
) : Serializable

/**
 * Data transfer object for detailed anime information.
 * @author Udit
 */
data class AnimeDetailsDto(
    @SerializedName(AppConstants.SERIAL_KEY_MAL_ID) val malId: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Float?,
    val images: ImagesDto,
    val trailer: TrailerDto?,
    val genres: List<GenreDto>
) : Serializable

/**
 * Data transfer object for anime genres.
 * @author Udit
 */
data class GenreDto(
    val name: String
) : Serializable

/**
 * Response wrapper for anime characters from API.
 * @author Udit
 */
data class AnimeCharactersResponse(
    val data: List<CharacterWrapperDto>
) : Serializable

/**
 * Data transfer object for character wrapper containing character information.
 * @author Udit
 */
data class CharacterWrapperDto(
    val character: CharacterDto
) : Serializable

/**
 * Data transfer object for character information.
 * @author Udit
 */
data class CharacterDto(
    val name: String
) : Serializable




