package com.developer.ivan.data.models

import com.developer.ivan.domain.Beer
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type


class BeerApiMother {

    val moshi: Moshi = Moshi.Builder().build()


    fun givenASuccessfullGetBeersResponse(
        beer: Beer
    ) {
        val type: Type = Types.newParameterizedType(List::class.java,String::class.java)
        val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter(type)

        with(beer) {
            val fakeResponse =
                """
                [
                  {
                    "id": ${id},
                    "name": "$name",
                    "tagline": "$tagline",
                    "first_brewed": "09/2007",
                    "description": "$description",
                    "image_url": "$imageUrl",
                    "abv": $alcoholByVolume,
                    "ibu": $ibu,
                    "target_fg": 1010,
                    "target_og": 1044,
                    "ebc": 20,
                    "srm": 10,
                    "ph": 4.4,
                    "attenuation_level": 75,
                    "volume": {
                      "value": 20,
                      "unit": "litres"
                    },
                    "boil_volume": {
                      "value": 25,
                      "unit": "litres"
                    },
                    "method": {
                      "mash_temp": [
                        {
                          "temp": {
                            "value": 64,
                            "unit": "celsius"
                          },
                          "duration": 75
                        }
                      ],
                      "fermentation": {
                        "temp": {
                          "value": 19,
                          "unit": "celsius"
                        }
                      },
                      "twist": null
                    },
                    "ingredients": {
                      "malt": [
                        {
                          "name": "Maris Otter Extra Pale",
                          "amount": {
                            "value": 3.3,
                            "unit": "kilograms"
                          }
                        },
                        {
                          "name": "Caramalt",
                          "amount": {
                            "value": 0.2,
                            "unit": "kilograms"
                          }
                        },
                        {
                          "name": "Munich",
                          "amount": {
                            "value": 0.4,
                            "unit": "kilograms"
                          }
                        }
                      ],
                      "hops": [
                        {
                          "name": "Fuggles",
                          "amount": {
                            "value": 25,
                            "unit": "grams"
                          },
                          "add": "start",
                          "attribute": "bitter"
                        },
                        {
                          "name": "First Gold",
                          "amount": {
                            "value": 25,
                            "unit": "grams"
                          },
                          "add": "start",
                          "attribute": "bitter"
                        },
                        {
                          "name": "Fuggles",
                          "amount": {
                            "value": 37.5,
                            "unit": "grams"
                          },
                          "add": "middle",
                          "attribute": "flavour"
                        },
                        {
                          "name": "First Gold",
                          "amount": {
                            "value": 37.5,
                            "unit": "grams"
                          },
                          "add": "middle",
                          "attribute": "flavour"
                        },
                        {
                          "name": "Cascade",
                          "amount": {
                            "value": 37.5,
                            "unit": "grams"
                          },
                          "add": "end",
                          "attribute": "flavour"
                        }
                      ],
                      "yeast": "Wyeast 1056 - American Ale™"
                    },
                    "food_pairing": ${jsonAdapter.toJson(foodPairing)},
                    "brewers_tips": "The earthy and floral aromas from the hops can be overpowering. Drop a little Cascade in at the end of the boil to lift the profile with a bit of citrus.",
                    "contributed_by": "Sam Mason <samjbmason>"
                  }
                ]
            """

        }
    }
}