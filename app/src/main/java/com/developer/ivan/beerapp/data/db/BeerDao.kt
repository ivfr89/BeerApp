package com.developer.ivan.beerapp.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BeerDao{

    @Query("SELECT * FROM DBBeer")
    fun getAllBeers() : Flow<List<DBBeer>>

    @Query("SELECT * FROM DBBeer WHERE id=:id")
    fun getBeer(id: Int) : Flow<DBBeer?>

    @Query("SELECT COUNT(*) FROM DBBeer")
    fun getBeersCount() : Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBeers(dbBeer: List<DBBeer>)

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateBeer(dbBeer: DBBeer)


}