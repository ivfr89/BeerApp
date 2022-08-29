package com.developer.ivan.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BeerDao {

    @Query("SELECT * FROM BeerDb")
    fun getAllBeers(): List<BeerDb>

    @Query("SELECT * FROM BeerDb WHERE id=:id")
    fun getBeer(id: Int): BeerDb?

    @Query("SELECT COUNT(*) FROM BeerDb")
    fun getBeersCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBeers(beerDb: List<BeerDb>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateBeer(beerDb: BeerDb)
}
