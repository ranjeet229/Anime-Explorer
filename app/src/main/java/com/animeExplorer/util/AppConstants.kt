package com.animeExplorer.util

/**
 * Application constants for UI strings and configuration values.
 * @author udit
 */
object AppConstants {
    // API Configuration
    const val BASE_URL = "https://api.jikan.moe/"
    const val NETWORK_TIMEOUT = 30L
    const val TOP_ANIME_ENDPOINT = "v4/top/anime"
    const val ANIME_DETAILS_ENDPOINT = "v4/anime/{id}"
    const val ANIME_CHARACTERS_ENDPOINT = "v4/anime/{id}/characters"
    
    // Database Configuration
    const val DATABASE_NAME = "anime_explorer_db"
    const val ANIME_TABLE_NAME = "anime"
    const val ANIME_DETAILS_TABLE_NAME = "anime_details"
    
    // Navigation
    const val ANIME_LIST_ROUTE = "animeList"
    const val ANIME_DETAILS_ROUTE = "animeDetails"
    const val ANIME_ID_PARAM = "animeId"
    
    // Serialization Keys
    const val SERIAL_KEY_MAL_ID = "mal_id"
    const val SERIAL_KEY_IMAGE_URL = "image_url"
    const val SERIAL_KEY_LARGE_IMAGE_URL = "large_image_url"
    const val SERIAL_KEY_YOUTUBE_ID = "youtube_id"
    
    // App Titles
    const val ANIME_EXPLORER_TITLE = "Anime Explorer"
    const val ANIME_DETAILS_TITLE = "Anime Details"
    
    // Loading Messages
    const val LOADING_ANIME_MESSAGE = "Loading Anime..."
    const val LOADING_DETAILS_MESSAGE = "Loading Details..."
    const val LOADING_VIDEO_MESSAGE = "Loading video..."
    
    // Error Messages
    const val ERROR_TEXT = "Something went wrong"
    const val VIDEO_ERROR_TITLE = "Video Error"
    const val YOUTUBE_ERROR_PREFIX = "YouTube player error: "
    const val NO_TRAILER_AVAILABLE = "No Trailer Available"
    
    // Button Text
    const val RETRY_TEXT = "Try Again"
    const val BACK_BUTTON_CONTENT_DESCRIPTION = "Go back"
    
    // Content Descriptions
    const val ANIME_POSTER_CONTENT_DESCRIPTION = "Anime poster image"
    
    // Section Headers
    const val SYNOPSIS_LABEL = "Synopsis"
    const val GENRES_LABEL = "Genres"
    const val MAIN_CAST_LABEL = "Main Cast"
    
    // Character List
    const val CHARACTER_BULLET = "• "
    
    // Episode Text
    const val EPISODE_SINGULAR = "Episode"
    const val EPISODE_PLURAL = "Episodes"
    
    // Padding Values
    const val DEFAULT_PADDING = 16
    
    // Dimensions
    const val VIDEO_PLAYER_HEIGHT = 240
    const val CARD_HEIGHT = 140
    const val IMAGE_WIDTH = 100
    
    // Rating Format
    const val RATING_FORMAT = "%.1f"
    
    // Error Messages
    const val NETWORK_ERROR_MESSAGE = "Failed to load data. Please check your internet connection and try again."
    const val ANIME_LIST_ERROR_MESSAGE = "Failed to load anime list. Please check your internet connection."
    const val ANIME_DETAILS_ERROR_MESSAGE = "Failed to load anime details. Please check your internet connection."
    
    // Oops Messages
    const val OOPS_MESSAGE = "Oops!"
    
    // Legal Constraint Handling
    const val SHOW_ANIME_IMAGES = true // Toggle this for legal compliance
}