package com.animeExplorer.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.animeExplorer.util.AppConstants
import java.io.Serializable

/**
 * Room entity representing an anime item in the local database.
 * @author Udit
 */
@Entity(tableName = AppConstants.ANIME_TABLE_NAME)
data class AnimeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val episodes: Int?,
    val score: Float?,
    val imageUrl: String?,
    val largeImageUrl: String?,
    val youtubeId: String?
) : Serializable
