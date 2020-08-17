package com.developer.ivan.beerapp.ui.mapper

import com.developer.ivan.beerapp.ui.main.models.UIBeer
import com.developer.ivan.domain.Beer


class UIMapper {

    fun convertUIBeerToDomain(uiBeer: UIBeer): Beer {

        return with(uiBeer) {
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

    fun convertBeersToUIBeers(beers: List<Beer>) =
        beers.map { domain ->
            convertBeerToUIBeer(domain)
        }


    fun convertBeerToUIBeer(beer: Beer) =
        with(beer) {
            UIBeer(
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
