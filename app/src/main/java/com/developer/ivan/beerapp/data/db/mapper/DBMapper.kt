package com.developer.ivan.beerapp.data.db.mapper

import com.developer.ivan.beerapp.data.db.DBBeer
import com.developer.ivan.domain.Beer

class DBMapper {

    fun convertDBBeersToDomain(dbBeers: List<DBBeer>): List<Beer> =
        dbBeers.map { entity ->
            convertDBBeerToDomain(entity)
        }

    fun convertDBBeerToDomain(dbBeer: DBBeer): Beer {

        return with(dbBeer) {
            Beer(
                id = id,
                name = name,
                tagline = tagline,
                description = description,
                image_url = image_url,
                abv = abv,
                ibu = ibu,
                food_pairing = food_pairing,
                isAvailable = isAvailable
            )
        }


    }

    fun convertBeersToDBBeers(beers: List<Beer>) =
        beers.map { domain ->
            convertBeerToDBBeer(domain)
        }


    fun convertBeerToDBBeer(beer: Beer) =
        with(beer) {
            DBBeer(
                id = id,
                name = name,
                tagline = tagline,
                description = description,
                image_url = image_url,
                abv = abv,
                ibu = ibu,
                food_pairing = food_pairing,
                isAvailable = isAvailable
            )
        }

}
