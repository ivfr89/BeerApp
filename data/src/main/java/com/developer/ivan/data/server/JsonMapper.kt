package com.developer.ivan.data.server
import com.developer.ivan.data.server.entities.BeerApiModel
import com.developer.ivan.domain.Beer
import com.developer.ivan.domain.Either
import com.developer.ivan.domain.Failure
import com.squareup.moshi.Moshi
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class JsonMapper(private val moshi: Moshi) {

    object JsonSyntaxException : Failure.CustomFailure()


    inline fun <T> getArray(json: String, body: JsonMapper.(JSONArray)->T): T {
        return body(JSONArray(json))
    }

    private fun convertJsonToEntityBeer(json: String): Either<Failure, BeerApiModel> {
        return try {
            val data =
                moshi.adapter(BeerApiModel::class.java).fromJson(json)

            data?.let {
                Either.Right(it)
            } ?: kotlin.run {
                Either.Left(JsonSyntaxException)
            }

        } catch (e: JSONException) {
            Either.Left(JsonSyntaxException)
        }
    }

    private fun convertJsonToBeer(
        json: JSONObject,
        transform: (BeerApiModel) -> Either<Failure, Beer>
    ): Either<Failure, Beer> {
        return when (val result = convertJsonToEntityBeer(json.toString())) {
            is Either.Left -> result
            is Either.Right -> transform(result.b)
        }
    }

    fun convertJsonToBeers(
        json: JSONArray,
        transform: (BeerApiModel) -> Either<Failure, Beer>
    ): Either<Failure, List<Beer>> {

        val list = mutableListOf<Beer>()
        var result: Either<Failure, List<Beer>> = Either.Right(list)

        var index = 0
        var failure = false

        try {
            while (index < json.length() && !failure) {
                when (val convertion = convertJsonToBeer(json.getJSONObject(index), transform)) {
                    is Either.Left -> {
                        failure = true
                        result = convertion
                    }
                    is Either.Right -> {
                        list.add(convertion.b)
                    }
                }
                index++
            }
        }catch (e: JSONException){
            result = Either.Left(JsonSyntaxException)
        }
        return result
    }


}