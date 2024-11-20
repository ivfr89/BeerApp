package com.developer.ivan.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BeerDb::class],
    version = 1
)
abstract class DB : RoomDatabase() {
    abstract fun beerDao(): BeerDao

    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context, DB::class.java, "db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
