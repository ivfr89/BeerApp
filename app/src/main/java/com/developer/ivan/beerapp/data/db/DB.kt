package com.developer.ivan.beerapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DBBeer::class],
    version = 2
)
abstract class DB : RoomDatabase() {

    abstract fun beerDao(): BeerDao

}
