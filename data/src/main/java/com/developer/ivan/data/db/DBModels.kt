package com.developer.ivan.data.db

import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class Converters {

    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
        return moshi.adapter<ArrayList<String>>(listType).fromJson(value) ?: emptyList()
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {

        val listType = Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
        return moshi.adapter<List<String>>(listType).toJson(list)
    }
}


@Entity(primaryKeys = ["id"])
@TypeConverters(value = [Converters::class])
data class BeerDb(
    val id: Int,
    val name: String,
    val tagline: String?,
    val description: String?,
    val imageUrl: String?,
    val alcoholByVolume: Double?,
    val ibu: Float?,
    val foodPairing: List<String>,
    val isAvailable: Boolean
)
