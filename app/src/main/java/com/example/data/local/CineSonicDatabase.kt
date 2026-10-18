package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [ProjectEntity::class, ShotEntity::class, UsageEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CineSonicDatabase : RoomDatabase() {
    abstract fun dao(): CineSonicDao

    companion object {
        @Volatile
        private var INSTANCE: CineSonicDatabase? = null

        fun getDatabase(context: Context): CineSonicDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CineSonicDatabase::class.java,
                    "cinesonic_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
