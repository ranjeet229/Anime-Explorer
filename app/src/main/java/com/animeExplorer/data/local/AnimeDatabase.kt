package com.animeExplorer.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AnimeEntity::class, AnimeDetailsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AnimeDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
    abstract fun animeDetailsDao(): AnimeDetailsDao
}
