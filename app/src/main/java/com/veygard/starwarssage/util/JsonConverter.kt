package com.veygard.starwarssage.util

import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory


class JsonConverter {

    private val stringAdapter by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        val listMyData = Types.newParameterizedType(List::class.java, String::class.java)
        return@lazy moshi.adapter<List<String>>(listMyData)
    }

    @TypeConverter
    fun listStringToJson(list: List<String>) : String {
        val json = stringAdapter.toJson(list)
        return json
    }

    @TypeConverter
    fun formJsonToListString(json: String) : List<String>? {
        return stringAdapter.fromJson(json)
    }
}