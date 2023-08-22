package com.example.wally.data.cache.model

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.wally.data.api.model.Urls
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converter {
    private val gson = Gson()

    @TypeConverter
    fun fromUrls(url: Urls) : String? {
        return gson.toJson(url)
    }

    @TypeConverter
    fun toUrls(urls: String) : Urls{
        val objectType = object : TypeToken<Urls>() {}.type
        return gson.fromJson(urls, objectType)
    }
}