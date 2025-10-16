package com.animeExplorer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.animeExplorer.util.AppConstants
import java.io.Serializable

/**
 * Room entity representing detailed anime information in the local database.
 * @author Udit
 */
@Entity(tableName = AppConstants.ANIME_DETAILS_TABLE_NAME)
data class AnimeDetailsEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val synopsis: String?,
    val episodes: Int?,
    val score: Float?,
    val imageUrl: String?,
    val largeImageUrl: String?,
    val youtubeId: String?,
    val genres: String,
    val characters: String
) : Serializable
